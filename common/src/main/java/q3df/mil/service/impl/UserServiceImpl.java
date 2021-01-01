package q3df.mil.service.impl;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.dto.user.UserUpdateDto;
import q3df.mil.entities.contacts.Contact;
import q3df.mil.entities.enums.SystemRoles;
import q3df.mil.entities.role.Role;
import q3df.mil.entities.user.User;
import q3df.mil.exception.CustomException;
import q3df.mil.exception.CustomMailException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mail.MailSender;
import q3df.mil.mapper.user.UserMapper;
import q3df.mil.mapper.user.UserPreviewMapper;
import q3df.mil.mapper.user.UserRegistrationMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.repository.custom.CustomRepository;
import q3df.mil.security.model.ChangePasswordRequest;
import q3df.mil.security.model.PasswordRecovery;
import q3df.mil.service.UserService;
import q3df.mil.util.CopyPropertiesHelperClass;
import q3df.mil.util.SupClass;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static q3df.mil.exception.ExceptionConstants.USER_NF;

/**
 * most are logically described in this class,
 * so the description will be more accurate
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRegistrationMapper userRegistrationMapper;
    private final UserPreviewMapper userPreviewMapper;
    private final PasswordEncoder passwordEncoder;
    private final SupClass supClass;
    private final CopyPropertiesHelperClass copyPropertiesHelperClass;
    private final MailSender mailSender;
    private final CustomRepository customRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           UserRegistrationMapper userRegistrationMapper,
                           UserPreviewMapper userPreviewMapper,
                           PasswordEncoder passwordEncoder,
                           SupClass supClass,
                           CopyPropertiesHelperClass copyPropertiesHelperClass,
                           MailSender mailSender,
                           CustomRepository customRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRegistrationMapper = userRegistrationMapper;
        this.userPreviewMapper = userPreviewMapper;
        this.passwordEncoder = passwordEncoder;
        this.supClass = supClass;
        this.copyPropertiesHelperClass = copyPropertiesHelperClass;
        this.mailSender = mailSender;
        this.customRepository = customRepository;
    }


    /**
     * find all users with pagination
     * @param pageable page + amount of results
     * @return list of users previews
     */
    @Override
    public List<UserPreview> findAll(Pageable pageable) {
        return userRepository
                .findAllByDeleteFalse(pageable)
                .stream()
                .map(userPreviewMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * find user by id
     * @param id id of user
     * @return userDto
     * @throws UserNotFoundException if user doesn't exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    //cache just for example
    @org.springframework.cache.annotation.Cacheable(
            value = "justForExampleCacheForUsersWithIdLessThan50", condition = "#id<50")
    public UserDto findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return userMapper.toDto(byId.orElseThrow(() -> new UserNotFoundException(USER_NF + id)));

    }


    /**
     * save user
     * @param userRegistrationDto userDto
     * @return saved user
     * @throws q3df.mil.exception.LoginExistException if user with same login is already exist in db
     * @throws q3df.mil.exception.EmailExistException if user with same email is already exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public UserDto saveUser(UserRegistrationDto userRegistrationDto) {
        List<User> usersByEmailAndLogin = userRepository
                .findUsersByEmailOrLogin(userRegistrationDto.getEmail(), userRegistrationDto.getLogin());

        //check for same login or email in db
        supClass.checkForLoginAndEmail(usersByEmailAndLogin, userRegistrationDto);
        User user = userRegistrationMapper.fromDto(userRegistrationDto);
        User savedUser = userRepository.save(user);

        //creating and associating a role with user
        {
            Role role = Role.builder().role(SystemRoles.ROLE_USER).user(savedUser).build();
            savedUser.setRoles(Collections.singleton(role));
        }
        return userMapper.toDto(savedUser);
    }


    /**
     * update user
     * @param userDto userDto
     * @return updated user
     * @throws UserNotFoundException if user doesn't exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    //cache just for example
    @org.springframework.cache.annotation.CachePut(
            value = "justForExampleCacheForUsersWithIdLessThan50", condition = "#userDto.id<50")
    public UserDto updateUser(UserUpdateDto userDto) {
        User user = userRepository
                .findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(USER_NF + userDto.getId()));
        copyPropertiesHelperClass.copyUserProperties(userDto, user);
        return userMapper.toDto(user);
    }


    /**
     * delet user
     * @param id user id
     * @throws UserNotFoundException if user doesn't exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteUser(Long id) {
        User user;
        try {
            user = userRepository.getOne(id);
        } catch (EntityNotFoundException ex) {
            throw new UserNotFoundException(USER_NF+ id);
        }
        user.setDelete(true);
    }


    /**
     * change user password
     * @param cp cp contain user login + password + new password
     * @throws UserNotFoundException user with this login doesn't exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    @org.springframework.cache.annotation.CachePut(
            value = "verTokCache", key = "#cp.login")
    public void changePassword(ChangePasswordRequest cp) {
        Optional<User> byLogin = userRepository.findByLogin(cp.getLogin());
        User user = byLogin
                .orElseThrow(() -> new UserNotFoundException(USER_NF + cp.getLogin()));
        user.setPChange(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(cp.getNewPassword()));
    }


    /**
     * add role to user
     * @param userId user id
     * @param systemRoles role
     * @throws UserNotFoundException if user with userId doesn't exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void addRoleToUser(Long userId, SystemRoles systemRoles) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NF +userId));
        user.addRoles(Role.builder().role(systemRoles).user(user).build());
    }


    /**
     * delete role from user
     * @param userId user id
     * @param systemRoles role
     * @throws UserNotFoundException if user with userId doesn't exist in db
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteRoleFromUser(Long userId, SystemRoles systemRoles) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NF +userId));
        Set<Role> roles = user.getRoles();
        roles.removeIf(role -> role.getRole().equals(systemRoles));
    }


    /**
     * find user between dates with pagination
     * @param before date before
     * @param after date after
     * @param page page + amount of results
     * @return list of founded users
     */
    @Override
    public List<UserPreview> findUserBetweenDates(String before, String after, Pageable page) {
        try {
            return userRepository
                    .findByDeleteFalseAndBirthdayBetween(LocalDate.parse(before), LocalDate.parse(after), page)
                    .stream()
                    .map(userPreviewMapper::toDto)
                    .collect(Collectors.toList());
        } catch (java.time.format.DateTimeParseException ex) {
            throw new CustomException("Bad format of date!");
        }

    }

    /**
     * find users by country and city with pagination
     * @param country country
     * @param city city
     * @param page page + amount of results
     * @return list of founded users
     */
    @Override
    public List<UserPreview> findByCountryAndCity(String country, String city, Pageable page) {
        return userRepository
                .findByDeleteFalseAndCountryIgnoreCaseAndCityIgnoreCase(country, city, page)
                .stream()
                .map(userPreviewMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * find user by first and last Name
     * @param name name of user
     * @param surname surname of user
     * @param page page + amount of results
     * @return list of founded users
     */
    @Override
    public List<UserPreview> findByFirstNameAndLastName(String name, String surname, Pageable page) {
        String firstName = StringUtils.join("%", name, "%");
        String lastName = StringUtils.join("%", surname, "%");
        return userRepository
                .findByDeleteFalseAndFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(firstName, lastName, page)
                .stream()
                .map(userPreviewMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * sends a letter to the user's mail including an explanation and url,
     * which contains the generated code necessary for further password recovery
     * @param passwordRecovery just user login and email
     * @throws UserNotFoundException if user doesn't exist in db
     * @throws CustomException if email not match with login
     * @throws CustomMailException if letter is already been sent or email is not exist in a web
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void recoveryPassword(PasswordRecovery passwordRecovery) {
        Optional<User> byLogin = userRepository.findByLogin(passwordRecovery.getLogin());
        User user = byLogin
                .orElseThrow(() -> new UserNotFoundException("User with login " + passwordRecovery.getLogin() + " doesn't exist!"));
        if (!user.getEmail().equals(passwordRecovery.getEmail())) {
            throw new CustomException("Email is not match with login!");
        }
        if (user.getRecoveryCode() != null) {
            throw new CustomMailException("The letter has already been sent on your mail!");
        }
        String length;
        {
            length = passwordRecovery.getLogin().length() > 10
                    ? "" + passwordRecovery.getLogin().length()
                    : "0" + passwordRecovery.getLogin().length();
        }
        String recoveryCode = UUID.randomUUID().toString();
        String generatedString = RandomStringUtils.randomAlphanumeric(8);
        String message = StringUtils.join("Hello, ",
                user.getFirstName(),
                "! \nPlease follow the link to indicate that it is really you want change password!" +
                        "\nLink : \n http://localhost8080/recovery/",
                recoveryCode, length, passwordRecovery.getLogin(), generatedString,
                " \nYour new password is ",
                generatedString);
        user.setRecoveryCode(recoveryCode);
        try{
        mailSender.send(passwordRecovery.getEmail(), "Recovery code!", message);
        }catch (MailSendException ex){
            throw new CustomMailException("Can't find mail in a web!");
        }
    }


    /**
     * checking input recovery code and if its valid change user password
     * @param recoveryCode recovery code from user mail
     * @return true - if password was change
     * @throws CustomException if recovery code is not correct
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public boolean applyingNewPassword(String recoveryCode) {
        final String recoveryCodeException = "Invalid recoveryCode!";
        String length = StringUtils.substring(recoveryCode, 36, 38);
        int a;
        try {
            a = Integer.parseInt(length);
        } catch (NumberFormatException ex) {
            throw new CustomException(recoveryCodeException);
        }
        String login = StringUtils.substring(recoveryCode, 38, 38 + a);
        String newPass = StringUtils.substring(recoveryCode, 38 + a);

        Optional<User> byLogin = userRepository.findByLogin(login);
        User user = byLogin
                .orElseThrow(() -> new CustomException(recoveryCodeException));
        if (user.getRecoveryCode() == null) {
            throw new CustomException(recoveryCodeException);
        }
        if (!recoveryCode.contains(user.getRecoveryCode())) {
            throw new CustomException(recoveryCodeException);
        }
        user.setRecoveryCode(null);
        user.setPassword(passwordEncoder.encode(newPass));
        return true;
    }


    //quick method just for show embeddable
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void updateContact(Long id, Contact contact) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NF +id));
        user.setContact(contact);
    }

    //quick method just for show embeddable
    @Override
    public Contact getUserContacts(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NF +id));
        return user.getContact();
    }


    /**
     * method showing statistics (number) of users by country and city
     * @return statistic of registered users
     */
    @Override
    public List<?> showCountOfUserByCityAndCountry() {
        return customRepository.showCountOfUserByCityAndCountry();
    }


    /**
     * shows a list of users by various criteria with pagination
     * note: this method replaces all methods above with searc by params and pagination
     * a list of this methods:
     * findUserBetweenDates()
     * findByCountryAndCity()
     * findByFirstNameAndLastName()
     * @param params user data by which the search occurs (6 fields) + 2 fields for number of page and number of results
     * @return list of founded users
     */
    @Override
    public List<UserPreview> showUsersByParamsWithPaginationByCriteria(String... params) {
        return customRepository.showUsersByParams(params);
    }


}
