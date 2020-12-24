package q3df.mil.service;

import q3df.mil.dto.message.MessageDto;
import q3df.mil.dto.message.MessageSaveDto;
import q3df.mil.dto.message.MessageUpdateDto;

import java.util.List;

public interface MessageService {

    List<MessageDto> findMessagesByDialogId(Long id);

    void deleteById(Long id);

    MessageDto saveMessage(MessageSaveDto messageSaveDto);

    MessageDto updateMessage(MessageUpdateDto messageUpdateDto);

}
