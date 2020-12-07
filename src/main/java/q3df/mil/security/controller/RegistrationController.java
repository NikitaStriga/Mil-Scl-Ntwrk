package q3df.mil.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;

    }


    @PostMapping
    public ResponseEntity<Map<String,Object>> registration(@RequestBody UserRegistrationDto userRegistrationDto){
        Map<String,Object> map=new HashMap<>();
        UserDto userDto = userService.saveUser(userRegistrationDto);
        map.put("id",userDto.getId());
        map.put("login",userRegistrationDto.getLogin());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
