package q3df.mil.security.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;

    }


    @ApiOperation(value = "Registration of user", notes = "All params are essential")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User was created successfully"),
            @ApiResponse(code = 400, message = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<Map<String,Object>> registration(@Valid @RequestBody UserRegistrationDto userRegistrationDto){
        Map<String,Object> map=new HashMap<>();
        UserDto userDto = userService.saveUser(userRegistrationDto);
        map.put("id",userDto.getId());
        map.put("login",userRegistrationDto.getLogin());
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

}
