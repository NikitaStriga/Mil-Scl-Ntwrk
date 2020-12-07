package q3df.mil.myfeatures;

import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.entities.message.Message;
import q3df.mil.entities.photo.Photo;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.entities.role.Role;
import q3df.mil.entities.text.Text;
import q3df.mil.entities.text.TextComment;
import q3df.mil.entities.text.TextCommentLike;
import q3df.mil.entities.text.TextLike;
import q3df.mil.entities.user.User;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.exception.EmailExistException;
import q3df.mil.exception.LoginExistException;
import q3df.mil.exception.MessageNotFoundException;
import q3df.mil.exception.PhotoCommentLikeNotFoundException;
import q3df.mil.exception.PhotoLikeNotFoundException;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.exception.RoleNotFoundException;
import q3df.mil.exception.TextCommentLikeNotFoundException;
import q3df.mil.exception.TextCommentNotFoundException;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;

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


    public static void findException(Class<?> classType, Long id){
        String message =  classType.getSimpleName() + " with id " + id + " doesn't exist!";
        if(classType.equals(User.class)) throw  new UserNotFoundException(message);
        if(classType.equals(Text.class)) throw  new TextNotFoundException(message);
        if(classType.equals(TextComment.class)) throw  new TextCommentNotFoundException(message);
        if(classType.equals(TextCommentLike.class)) throw  new TextCommentLikeNotFoundException(message);
        if(classType.equals(TextLike.class)) throw  new TextCommentNotFoundException(message);
        if(classType.equals(Role.class)) throw  new RoleNotFoundException(message);
        if(classType.equals(Photo.class)) throw  new PhotoNotFoundException(message);
        if(classType.equals(PhotoComment.class)) throw  new RoleNotFoundException(message);
        if(classType.equals(PhotoLike.class)) throw  new PhotoLikeNotFoundException(message);
        if(classType.equals(PhotoCommentLike.class)) throw  new PhotoCommentLikeNotFoundException(message);
        if(classType.equals(Message.class)) throw  new MessageNotFoundException(message);
        if(classType.equals(Dialog.class)) throw  new DialogNotFoundException(message);

    }



}
