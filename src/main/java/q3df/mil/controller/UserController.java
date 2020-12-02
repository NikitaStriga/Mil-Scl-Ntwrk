package q3df.mil.controller;


import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import q3df.mil.dto.dialog_message_dto.DialogDto;
import q3df.mil.dto.dialog_message_dto.MessageDto;
import q3df.mil.dto.UserDto;
import q3df.mil.dto.UserRegistrationDto;
import q3df.mil.mapper.UserMapper;
import q3df.mil.mapper.UserRegistrationMapper;
import q3df.mil.repository.UserRepository;
import q3df.mil.service.DialogService;
import q3df.mil.service.MessageService;
import q3df.mil.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final DialogService dialogService;
    private final MessageService messageService;
    private final UserMapper userMapper;
    private final UserRegistrationMapper userRegistrationMapper;


    public UserController(UserService userService, UserRepository userRepository, DialogService dialogService, MessageService messageService, UserMapper userMapper, UserRegistrationMapper userRegistrationMapper, StringTrimmerEditor stringTrimmerEditor) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.dialogService = dialogService;
        this.messageService = messageService;
        this.userMapper = userMapper;
        this.userRegistrationMapper = userRegistrationMapper;
    }



    @GetMapping
    public ResponseEntity<List<UserDto>> findUsers(){
        return ResponseEntity.ok(userService.findAll());
    }


    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
            return ResponseEntity.ok(userService.findById(id));
    }


    @GetMapping("{id}/dialog")
    public ResponseEntity<List<DialogDto>> findUserDialogs(@PathVariable Long id){
        return ResponseEntity.ok(dialogService.findDialogsByUserId(id));
    }


    @GetMapping("{id}/dialog/{dialogId}")
    public ResponseEntity<List<MessageDto>> findMessages(
            @PathVariable(value = "id") Long id,@PathVariable(value = "dialogId") Long dialogId
    ){
        return ResponseEntity.ok(messageService.findMessagesByDialogId(dialogId));
    }


    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto)  {

        return ResponseEntity.ok(userService.saveUser(userRegistrationDto));

    }



}
