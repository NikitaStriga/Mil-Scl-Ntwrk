package q3df.mil.security.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.mail.MailSender;
import q3df.mil.security.model.PasswordRecovery;
import q3df.mil.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recovery")
public class PasswordRecoveryController {

    private final UserService userService;

    @Autowired
    public PasswordRecoveryController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Password recovery request",
            notes = "If password and login exist in system then letter will be sent to the user's mail with further instructions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Mail was sent to user"),
            @ApiResponse(code = 401, message = "Invalid login or email"),
            @ApiResponse(code = 401, message = "Mail not matches for this login")
    })
    @PostMapping
    public ResponseEntity<String> recoveryPassword(@Valid @RequestBody PasswordRecovery passwordRecovery){
        userService.recoveryPassword(passwordRecovery);
        return ResponseEntity.ok("Visit your email to confirm password change");
    }


    @ApiOperation(value = "Password recovery request", notes = "User need to have a code that he received in a mail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Password was changed"),
            @ApiResponse(code = 404, message = "Bad recovery code")
    })
    @PostMapping("{code}")
    public ResponseEntity<Map<String,String>> applyRecoveryOfPassword(@PathVariable String code){
        Map<String,String> map = new HashMap<>();
        userService.applyingNewPassword(code);
        map.put("message","Password was successfully recovered!");
        map.put("step to login","Visit http://localhost8080/authentication to login into system! ");
        map.put("step to change password","Visit http://localhost8080/password to change the password to your own! ");
        return ResponseEntity.ok(map);
    }




}
