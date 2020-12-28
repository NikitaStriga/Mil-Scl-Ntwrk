package q3df.mil.security.exceptionhandling.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * this class is essential to implement custom error handling for authorization exception
 */
@Component
@Log4j2
public class JwtAuthorizationExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        //set response code
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        //set type of response
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        //message for response
        String message;

        //get user details from spring context
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        //get principal
        Object principal = auth.getPrincipal();

        //get userDetails
        UserDetails userDetails = (UserDetails) principal;

        //log user who wanted to enter the forbidden zone ... how did he know about this path ... hmm ...?
        // Probably he is a hacker ... need to ban him  :)
        log.error(userDetails.getUsername());

        //get exception
        if (accessDeniedException.getCause() != null) {
            message = accessDeniedException.getCause().toString() + " " + accessDeniedException.getMessage();
        } else {
            message = accessDeniedException.getMessage();
        }

        //put data
//        Map<String, String> map = new HashMap<>();
//        map.put("error", message);
//        map.put(
//                "userDetails",
//                userDetails.getUsername() +
//                        userDetails
//                                .getAuthorities()
//                                .stream()
//                                .map(GrantedAuthority::getAuthority)
//                                .collect(Collectors.joining(",")));

        //get bytes
        byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error",message));

        //set response message
        response.getOutputStream().write(body);
    }

}

