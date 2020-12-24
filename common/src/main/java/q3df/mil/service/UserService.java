package q3df.mil.service;


import org.springframework.data.domain.Pageable;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.dto.user.UserUpdateDto;
import q3df.mil.entities.contacts.Contact;
import q3df.mil.entities.enums.SystemRoles;
import q3df.mil.security.model.ChangePasswordRequest;
import q3df.mil.security.model.PasswordRecovery;

import java.util.List;


public interface UserService {

    //standard
    List<UserPreview> findAll(Pageable page);

    UserDto findById(Long id);

    UserDto saveUser(UserRegistrationDto userRegistrationDto);

    UserDto updateUser(UserUpdateDto user);

    void deleteUser(Long id);


    //for security
    void changePassword(ChangePasswordRequest cp);

    void addRoleToUser(Long userId, SystemRoles systemRoles);

    void deleteRoleFromUser(Long userId, SystemRoles systemRoles);

    void recoveryPassword(PasswordRecovery passwordRecovery);

    boolean applyingNewPassword(String recoveryCode);


    //pageable + sort + search
    //by birthday between ...
    List<UserPreview> findUserBetweenDates(String before, String after, Pageable page);

    //by country + city
    List<UserPreview> findByCountryAndCity(String country, String city, Pageable page);

    //by firstName and Surname
    List<UserPreview> findByFirstNameAndLastName(String name, String surname, Pageable page);

    //quick method just for show embeddable
    void updateContact(Long id, Contact contact);

    //get user contacts
    Contact getUserContacts(Long id);

    //from custom repo
    List<?> showCountOfUserByCityAndCountry();

    List<UserPreview> showUsersByParamsWithPaginationByCriteria(String... params);


}
