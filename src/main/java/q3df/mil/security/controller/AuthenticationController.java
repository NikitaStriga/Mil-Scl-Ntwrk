package q3df.mil.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.security.model.AuthRequest;
import q3df.mil.security.model.AuthResponse;
import q3df.mil.security.util.TokenUtils;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;

    private final UserDetailsService userProvider;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenUtils tokenUtils, @Qualifier("userServiceProvider") UserDetailsService userProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userProvider = userProvider;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {

        /*Check login and password.  uses our method loadUserByUsername in  UserServiceProvide  */
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );

        /* put information (it will be login,password and roles)
         in Spring Security Context about successful user authentication */
//        SecurityContextHolder.getContext().setAuthentication(authenticate);

            //return response with generated token
            return ResponseEntity.ok(
                    AuthResponse
                            .builder()
                            .username(request.getLogin())
                            .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getLogin())))
                            .build()
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username|password!");
        }
    }
}
