package q3df.mil.controller.message;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.service.MessageService;

import java.net.URI;


@RestController
@RequestMapping("/users/users/{id}/dialogs/{dialogId}/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDto> getMessage(@PathVariable Long messageId){
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping
    public ResponseEntity<?> saveMessage(@RequestBody MessageDto messageDto){
        MessageDto savedDto = messageService.saveMessage(messageDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedDto.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping
    public ResponseEntity<MessageDto> updateMessage(@RequestBody MessageDto messageDto){
        return  ResponseEntity.ok(messageService.saveMessage(messageDto));
    }


    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId){
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }








}
