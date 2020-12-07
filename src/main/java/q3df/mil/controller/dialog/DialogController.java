package q3df.mil.controller.dialog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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


    @GetMapping()
    public ResponseEntity<List<DialogDto>> findUserDialogs(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(dialogService.findDialogsByUserId(id));
    }


    @GetMapping("/{dialogId}")
    public ResponseEntity<List<MessageDto>> findMessages(@PathVariable(value = "dialogId") Long dialogId) {
        return ResponseEntity.ok(messageService.findMessagesByDialogId(dialogId));
    }


    @PostMapping
    public ResponseEntity<?> saveDialog(@RequestBody DialogDto dialogDto){
        DialogDto savedDialog = dialogService.saveDialog(dialogDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedDialog.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @DeleteMapping("/{dialogId}")
    public ResponseEntity<?> deleteDialog(@PathVariable Long dialogId){
        dialogService.deleteById(dialogId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //    //q
//    @PutMapping("/{dialogId}")
//    public ResponseEntity<DialogDto> updateMessage(@PathVariable Long dialogId, DialogDto dialogDto){
//        return null;
//    }
}
