package q3df.mil.myfeatures;

import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.entities.user.User;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.LoginExistException;

import java.util.List;

public class SupClass {

    // checks if the login or email matches other users
    public static void checkForLoginAndEmail(List<User> userList, UserRegistrationDto userRegistrationDto) {
        if(!userList.isEmpty()){
            for (User user : userList) {
                if(user.getLogin().equalsIgnoreCase(userRegistrationDto.getLogin())){
                    throw new LoginExistException("Login " + userRegistrationDto.getLogin() + " is already exists!");
                }
                if(user.getEmail().equalsIgnoreCase(userRegistrationDto.getEmail())){
                    throw new EmailExistException("Email " + userRegistrationDto.getEmail() + " is already exists!");
                }
            }
        }
    }



}
