package q3df.mil.service;

import q3df.mil.dto.message.MessageDto;

import java.util.List;

public interface MessageService {

    List<MessageDto> findMessagesByDialogId(Long id);

    int deleteMessagesByUser(Long id);

}
