package q3df.mil.controller.message;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import q3df.mil.dto.message.MessageSaveDto;
import q3df.mil.dto.message.MessageUpdateDto;
import q3df.mil.service.MessageService;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/users/users/{id}/dialogs/{dialogId}/message")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @ApiOperation(value = "Add message",
            notes = "If this is the first message between users, then the diaolog will be created automatically")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Message was saved"),
            @ApiResponse(code = 400, message = "Some of users not belong to dialog"),
            @ApiResponse(code = 404, message = "Users from message not found")
    })
    @PostMapping
    public ResponseEntity<MessageDto> saveMessage(@Valid @RequestBody MessageSaveDto messageSaveDto) {
        MessageDto savedDto = messageService.saveMessage(messageSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedDto.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update message")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Message was updated"),
            @ApiResponse(code = 404, message = "Message not found")
    })
    @PutMapping
    public ResponseEntity<MessageDto> updateMessage(@Valid @RequestBody MessageUpdateDto messageUpdateDto) {
        return ResponseEntity.ok(messageService.updateMessage(messageUpdateDto));
    }


    @ApiOperation(value = "Delete message")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Message was deleted"),
            @ApiResponse(code = 404, message = "Message not found")
    })
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteById(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
