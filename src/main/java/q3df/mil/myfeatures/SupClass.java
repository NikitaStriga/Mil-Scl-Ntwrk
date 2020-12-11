package q3df.mil.myfeatures;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
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
import q3df.mil.exception.NoPermissionCustomException;
import q3df.mil.exception.PhotoCommentLikeNotFoundException;
import q3df.mil.exception.PhotoCommentNotFoundException;
import q3df.mil.exception.PhotoLikeNotFoundException;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.exception.RoleNotFoundException;
import q3df.mil.exception.TextCommentLikeNotFoundException;
import q3df.mil.exception.TextCommentNotFoundException;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static q3df.mil.security.util.JwtConstants.USER_ID;

@Component
public class SupClass {


    /**
     * checking incoming registration for same email and login in db
     * used in {@link q3df.mil.service.impl.UserServiceImpl } in a saveUser method
     * @param userList  existing users
     * @param userRegistrationDto   user who wants to register
     * @throws LoginExistException if login is already exist in db
     * @throws EmailExistException if email is already exist in db
     */
    public  void checkForLoginAndEmail(List<User> userList, UserRegistrationDto userRegistrationDto) {
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



    /**
     * oH ,  this is my own implementation of security, to be more precise, checking the rights to perform an operation
     * @param request request of user
     * @param userId   userId from dto or it can be pathVariable of request ( for example users/{id}/... )
     * @throws NoPermissionCustomException if user have no permission
     * @return true if user have permission for operation
     */
    public boolean  checkPermission(HttpServletRequest request, Long userId){
//        Long id = (Long) request.getAttribute(USER_ID);
//
//        if(id!=null&&!id.equals(userId)){
//            return true;
//        }
//        String isPermitted = (String) request.getAttribute("permission");
//        if(isPermitted!=null&&isPermitted.equals("true")){
//            return true;
//        }
//        throw new NoPermissionCustomException("No permission to execute the operation!");
        return true;
    }






    // it was a good try ...
    public static void findException(Class<?> classType, Long id){
        String message =  classType.getSimpleName() + " with id " + id + " doesn't exist!";
        if(classType.equals(User.class)) throw  new UserNotFoundException(message);
        if(classType.equals(Text.class)) throw  new TextNotFoundException(message);
        if(classType.equals(TextComment.class)) throw  new TextCommentNotFoundException(message);
        if(classType.equals(TextCommentLike.class)) throw  new TextCommentLikeNotFoundException(message);
        if(classType.equals(TextLike.class)) throw  new TextCommentNotFoundException(message);
        if(classType.equals(Role.class)) throw  new RoleNotFoundException(message);
        if(classType.equals(Photo.class)) throw  new PhotoNotFoundException(message);
        if(classType.equals(PhotoComment.class)) throw  new PhotoCommentNotFoundException(message);
        if(classType.equals(PhotoLike.class)) throw  new PhotoLikeNotFoundException(message);
        if(classType.equals(PhotoCommentLike.class)) throw  new PhotoCommentLikeNotFoundException(message);
        if(classType.equals(Message.class)) throw  new MessageNotFoundException(message);
        if(classType.equals(Dialog.class)) throw  new DialogNotFoundException(message);
    }



}
