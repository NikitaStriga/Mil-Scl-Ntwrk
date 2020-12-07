package q3df.mil.service;

import q3df.mil.dto.message.MessageDto;

import java.util.List;

public interface MessageService {

//    MessageDto findById(Long id);

    List<MessageDto> findMessagesByDialogId(Long id);

    void deleteById(Long id);

    MessageDto saveMessage(MessageDto messageDto);

}
