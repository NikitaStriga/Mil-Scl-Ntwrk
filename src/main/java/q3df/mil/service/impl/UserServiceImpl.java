package q3df.mil.service.impl;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import q3df.mil.dto.user.UserUpdateDto;
import q3df.mil.exception.CustomException;
import q3df.mil.mail.MailSender;
import q3df.mil.myfeatures.CopyPropertiesHelperClass;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.entities.enums.SystemRoles;
import q3df.mil.entities.role.Role;
import q3df.mil.entities.user.User;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.user.UserPreviewMapper;
import q3df.mil.mapper.user.UserMapper;
import q3df.mil.mapper.user.UserRegistrationMapper;
import q3df.mil.myfeatures.SupClass;
import q3df.mil.repository.UserRepository;
import q3df.mil.security.model.ChangePasswordRequest;
import q3df.mil.security.model.PasswordRecovery;
import q3df.mil.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           UserRegistrationMapper userRegistrationMapper,
                           UserPreviewMapper userPreviewMapper,
                           PasswordEncoder passwordEncoder,
                           SupClass supClass,
                           CopyPropertiesHelperClass copyPropertiesHelperClass,
                           MailSender mailSender) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRegistrationMapper = userRegistrationMapper;
        this.userPreviewMapper = userPreviewMapper;
        this.passwordEncoder = passwordEncoder;
        this.supClass = supClass;
        this.copyPropertiesHelperClass = copyPropertiesHelperClass;
        this.mailSender = mailSender;
    }



    @Override
    public List<UserPreview> findAll(Pageable pageable) {
        return userRepository
                .findAllByDeleteFalse(pageable)
                .stream()
                .map(userPreviewMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public UserDto findById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return userMapper
                .toDto(byId.orElseThrow(()-> new UserNotFoundException("User with id " + id + " not found!")));

    }



    @Override
    @Transactional
    public UserDto saveUser(UserRegistrationDto userRegistrationDto) {
        List<User> usersByEmailAndLogin = userRepository
                .findUsersByEmailOrLogin(userRegistrationDto.getEmail(), userRegistrationDto.getLogin());
        //check for same login or email in db
        supClass.checkForLoginAndEmail(usersByEmailAndLogin,userRegistrationDto);
        User user = userRegistrationMapper.fromDto(userRegistrationDto);
        User savedUser = userRepository.save(user);
        //creating and associating a role with user
        {
            Role role = Role.builder().role(SystemRoles.ROLE_USER).user(savedUser).build();
            savedUser.setRoles(Collections.singletonList(role));
        }
        return userMapper.toDto(savedUser);


    }


    @Override
    @Transactional
    public UserDto updateUser(UserUpdateDto userDto) {
        User user;
        try{
            user=userRepository.getOne(userDto.getId());
        }catch (EntityNotFoundException ex){
            throw new UserNotFoundException("User with id " + userDto.getId() + " doesn't exist!");
        }
        copyPropertiesHelperClass.copyUserProperties(userDto, user);
        return userMapper.toDto(user);
    }


    @Override
    public void deleteUser(Long id) {
        User user;
        try{
            user=userRepository.getOne(id);
        }catch (EntityNotFoundException ex){
            throw new UserNotFoundException("User with id " + id + " doesn't exist!");
        }
        user.setDelete(true);
    }


    @Override
    public void changePassword(ChangePasswordRequest cp) {
        Optional<User> byLogin = userRepository.findByLogin(cp.getLogin());
        User user = byLogin
                .orElseThrow(() -> new UserNotFoundException("User with login " + cp.getLogin() + " doesn't exist!"));
        user.setPChange(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(cp.getNewPassword()));
    }

    @Override
    public void addRoleToUser(Long userId, SystemRoles systemRoles) {
        User user;
        try{
            user=userRepository.getOne(userId);
        }catch (EntityNotFoundException ex){
            throw new UserNotFoundException("User with id " + userId + " doesn't exist!");
        }
        user.addRoles(Role.builder().role(systemRoles).user(user).build());
    }


    @Override
    public void deleteRoleFromUser(Long userId, SystemRoles systemRoles) {
        User user;
        try{
            user=userRepository.getOne(userId);
        }catch (EntityNotFoundException ex){
            throw new UserNotFoundException("User with id " + userId + " doesn't exist!");
        }
        List<Role> roles = user.getRoles();
        roles.removeIf(role -> role.getRole().equals(systemRoles));
    }



    @Override
    public List<UserPreview> findUserBetweenDates(String before, String after, Pageable page) {
        try{
            return userRepository
                    .findByDeleteFalseAndBirthdayBetween(LocalDate.parse(before), LocalDate.parse(after), page)
                    .stream()
                    .map(userPreviewMapper::toDto)
                    .collect(Collectors.toList());
        }catch (java.time.format.DateTimeParseException ex){
            throw new CustomException("Bad format of date!");
        }

    }

    @Override
    public List<UserPreview> findByCountryAndCity(String country, String city, Pageable page) {
        return userRepository
                .findByDeleteFalseAndCountryIgnoreCaseAndCityIgnoreCase(country,city,page)
                .stream()
                .map(userPreviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserPreview> findByFirstNameAndLastName(String name, String surname, Pageable page) {
        String firstName = StringUtils.join("%", name, "%");
        String lastName = StringUtils.join("%",surname,"%");
        return userRepository
                .findByDeleteFalseAndFirstNameLikeIgnoreCaseAndLastNameLikeIgnoreCase(firstName,lastName,page)
                .stream()
                .map(userPreviewMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void recoveryPassword(PasswordRecovery passwordRecovery) {
        Optional<User> byLogin = userRepository.findByLogin(passwordRecovery.getLogin());
        User user = byLogin
                .orElseThrow(() -> new UserNotFoundException("User with login " + passwordRecovery.getLogin() + " doesn't exist!"));
        if(user.getEmail().equals(passwordRecovery.getEmail())){
            throw new CustomException("Email is not match with login!");
        }
        String recoveryCode = UUID.randomUUID().toString();
        String generatedString = RandomStringUtils.randomAlphanumeric(8);
        String message = StringUtils.join("Hello, ",
                user.getFirstName(),
                "! \nPlease follow the link to indicate that it is really you want change password!" +
                        " Link : http://localhost8080/recovery/",
                recoveryCode,generatedString,passwordRecovery.getLogin(),
                " \nYour new password is ",
                generatedString);
        mailSender.send(passwordRecovery.getEmail(), "Recovery code!", message);
        user.setRecoveryCode(generatedString);
    }

    @Override
    public boolean applyingNewPassword(String recoveryCode) {
        String login = StringUtils.substring(recoveryCode, 36, 42);
        String newPass = StringUtils.substring(recoveryCode, 42);
        Optional<User> byLogin = userRepository.findByLogin(login);
        User user = byLogin
                .orElseThrow(() -> new CustomException("Invalid recoveryCode!"));
        if(!user.getRecoveryCode().equals(recoveryCode)){
            throw new CustomException("Invalid recoveryCode!");
        }
        user.setRecoveryCode(null);
        user.setPassword(newPass);
        return true;
    }

}
