package q3df.mil.controller.DialogController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import q3df.mil.dto.dialog_message_dto.DialogDto;
import q3df.mil.dto.dialog_message_dto.MessageDto;
import q3df.mil.service.DialogService;
import q3df.mil.service.MessageService;

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


    @GetMapping()
    public ResponseEntity<List<DialogDto>> findUserDialogs(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(dialogService.findDialogsByUserId(id));
    }



    @GetMapping("/{dialogId}")
    public ResponseEntity<List<MessageDto>> findMessages(@PathVariable(value = "dialogId") Long dialogId) {
        return ResponseEntity.ok(messageService.findMessagesByDialogId(dialogId));
    }

    //q
    @PostMapping("/{dialogId}")
    public ResponseEntity<?> saveDialog(@PathVariable Long dialogId, DialogDto dialogDto){
        return null;
    }


    //q
    @PutMapping("/{dialogId}")
    public ResponseEntity<DialogDto> updateMessage(@PathVariable Long dialogId, DialogDto dialogDto){
        return null;
    }





    @DeleteMapping("/{dialogId}")
    public ResponseEntity<?> deleteDialog(@PathVariable Long dialogId){
        dialogService.deleteById(dialogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
