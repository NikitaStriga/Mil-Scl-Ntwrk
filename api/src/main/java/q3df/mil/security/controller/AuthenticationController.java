package q3df.mil.security.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.security.model.AuthRequest;
import q3df.mil.security.model.AuthResponse;
import q3df.mil.security.util.TokenUtils;

import javax.validation.Valid;

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


    @ApiOperation(value = "Authenticate and Create token",
            notes = "If  login and password are valid, a token will be generated and sent in the response")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login and password valid. Token was created successfully"),
            @ApiResponse(code = 401, message = "Invalid login or password!")
    })
    @PostMapping
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest request) {

        /*Check login and password.  uses our method loadUserByUsername in  UserServiceProvide
         * if login doesn't exist or password for this login ont valid it will be thrown AuthenticationException  */
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );


            //return response with generated token
            return ResponseEntity.ok(
                    AuthResponse
                            .builder()
                            .username(request.getLogin())
                            .token(tokenUtils.generateToken(userProvider.loadUserByUsername(request.getLogin())))
                            .build()
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password!");
        }
    }
}
