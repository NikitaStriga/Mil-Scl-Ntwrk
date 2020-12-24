package q3df.mil.security.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.security.model.ChangePasswordRequest;
import q3df.mil.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class ChangePasswordController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public ChangePasswordController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @ApiOperation(value = "Change password",
            notes = "If  login and password are valid, it will change user password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Password was successfully changed"),
            @ApiResponse(code = 401, message = "Invalid login or password")
    })
    @PostMapping
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest cp) {


        /*Check login and password.  uses our method loadUserByUsername in  UserServiceProvide
         * if login doesn't exist or password for this login ont valid it will be thrown AuthenticationException  */
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            cp.getLogin(),
                            cp.getPassword()
                    )
            );
            //save new password
            userService.changePassword(cp);
            //return response with generated token
            return ResponseEntity.ok("Password was successfully changed!");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid login or password!");
        }
    }


}
