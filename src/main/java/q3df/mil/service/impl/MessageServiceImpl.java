package q3df.mil.service.impl;


import org.springframework.stereotype.Service;
import q3df.mil.dto.dialog_message_dto.MessageDto;
import q3df.mil.entities.messages_dialogs.Message;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.mapper.dialog_message_mappers.MessageMapper;
import q3df.mil.repository.MessageRepository;
import q3df.mil.service.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }


    @Override
    public List<MessageDto> findMessagesByDialogId(Long id) {
        List<Message> messages= messageRepository.findMessagesByDialogId(id);
        if(messages.isEmpty()) throw new DialogNotFoundException("Dialog with id " + id + " does not exist!");
        return messages.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }
}
