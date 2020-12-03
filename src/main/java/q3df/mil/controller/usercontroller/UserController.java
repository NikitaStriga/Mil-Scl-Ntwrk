package q3df.mil.controller.usercontroller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.UserDto;
import q3df.mil.dto.UserRegistrationDto;
import q3df.mil.dto.UserForPreviewOfAllUsersDto;
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
    private final DialogService dialogService;
    private final MessageService messageService;

    public UserController(UserService userService, DialogService dialogService, MessageService messageService) {
        this.userService = userService;
        this.dialogService = dialogService;
        this.messageService = messageService;
    }


    @GetMapping
    public ResponseEntity<List<UserForPreviewOfAllUsersDto>> findUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
            return ResponseEntity.ok(userService.findById(id));
    }



    //q
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(userDto));
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



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        messageService.deleteMessagesByUser(id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







}
