package q3df.mil.controller.user;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.dto.user.UserForPreviewOfAllUsersDto;
import q3df.mil.service.DialogService;
import q3df.mil.service.MessageService;
import q3df.mil.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserForPreviewOfAllUsersDto>> findUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
            return ResponseEntity.ok(userService.findById(id));
    }



    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto)  {
        UserDto userDto = userService.saveUser(userRegistrationDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userDto.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







}
