package q3df.mil.util;

import org.springframework.stereotype.Component;
import q3df.mil.exception.NoPermissionCustomException;

import javax.servlet.http.HttpServletRequest;

import static q3df.mil.security.util.JwtConstants.USER_ID;

@Component
public class CustomPermission {

    /**
     * oH , this is my own implementation of security, to be more precise, checking the rights to perform an operation
     * note: if you want to know what is underlying of this method, see
     * {@link q3df.mil.security.util.TokenUtils} and {@link q3df.mil.security.filter.JwtTokenFilter
     * @param request request of user
     * @param userId  userId from dto or it can be pathVariable of request ( for example users/{id}/... )
     * @return true if user have permission for operation
     * @throws NoPermissionCustomException if user have no permission
     */
    public boolean checkPermission(HttpServletRequest request, Long userId) {
//        Long id = (Long) request.getAttribute(USER_ID);
//        if (id != null && !id.equals(userId)) {
//            return true;
//        }
//        String isPermitted = (String) request.getAttribute("permission");
//        if (isPermitted != null && isPermitted.equals("true")) {
//            return true;
//        }
//        throw new NoPermissionCustomException("No permission to execute the operation!");

        //delete after uncomment
        return true;
        ////////////////////////
    }

}
