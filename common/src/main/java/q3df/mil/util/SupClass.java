package q3df.mil.util;

import org.springframework.stereotype.Component;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.entities.user.User;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.LoginExistException;

import java.util.List;


@Component
public class SupClass {

    /**
     * checking incoming registration for same email and login in db
     * used in {@link q3df.mil.service.impl.UserServiceImpl } in a saveUser method}
     * @param userList            existing users
     * @param userRegistrationDto user who wants to register
     * @throws LoginExistException if login is already exist in db
     * @throws EmailExistException if email is already exist in db
     */
    public void checkForLoginAndEmail(List<User> userList, UserRegistrationDto userRegistrationDto) {
        if (!userList.isEmpty()) {
            for (User user : userList) {
                if (user.getLogin().equalsIgnoreCase(userRegistrationDto.getLogin())) {
                    throw new LoginExistException("Login " + userRegistrationDto.getLogin() + " is already exists!");
                }
                if (user.getEmail().equalsIgnoreCase(userRegistrationDto.getEmail())) {
                    throw new EmailExistException("Email " + userRegistrationDto.getEmail() + " is already exists!");
                }
            }
        }
    }


}
