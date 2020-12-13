package q3df.mil.controller.dialog;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.dto.dialog.DialogDto;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.service.DialogService;
import q3df.mil.service.MessageService;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users/{id}/dialogs")
public class DialogController {

    private final DialogService dialogService;
    private final MessageService messageService;

    public DialogController(DialogService dialogService, MessageService messageService) {
        this.dialogService = dialogService;
        this.messageService = messageService;
    }


    @ApiOperation(value = "Get user dialogs")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of user dialogs"),
            @ApiResponse(code = 404, message = "User with current id not found")
    })
    @GetMapping()
    public ResponseEntity<List<DialogDto>> findUserDialogs(@PathVariable Long id){
        return ResponseEntity.ok(dialogService.findDialogsByUserId(id));
    }


    @ApiOperation(value = "Get messages by dialog id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Messages of dialog"),
            @ApiResponse(code = 404, message = "Dialog with current id not found")
    })
    @GetMapping("/{dialogId}")
    public ResponseEntity<List<MessageDto>> findMessages(@PathVariable Long dialogId) {
        return ResponseEntity.ok(messageService.findMessagesByDialogId(dialogId));
    }


//    @PostMapping
//    public ResponseEntity<?> saveDialog(@RequestBody DialogDto dialogDto){
//        DialogDto savedDialog = dialogService.saveDialog(dialogDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedDialog.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
//    }


    @ApiOperation(value = "Delete dialog")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Dialog was successfully deleted"),
            @ApiResponse(code = 404, message = "Dialog with current id not found")
    })
    @DeleteMapping("/{dialogId}")
    public ResponseEntity<?> deleteDialog(@PathVariable Long dialogId){
        dialogService.deleteById(dialogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
