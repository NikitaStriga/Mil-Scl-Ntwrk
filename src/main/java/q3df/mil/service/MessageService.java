package q3df.mil.service;

import q3df.mil.dto.dialog_message_dto.MessageDto;

import java.util.List;

public interface MessageService {

    List<MessageDto> findMessagesByDialogId(Long id);

}
