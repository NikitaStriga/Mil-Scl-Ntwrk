package q3df.mil.service;


import q3df.mil.dto.dialog_message_dto.DialogDto;
import q3df.mil.entities.messages_dialogs.Dialog;

import java.util.List;

public interface DialogService {

    List<DialogDto> findDialogsByUserId(Long id);
}
