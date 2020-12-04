package q3df.mil.service.impl;


import org.springframework.stereotype.Service;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.entities.message.Message;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.mapper.message.MessageMapper;
import q3df.mil.repository.MessageRepository;
import q3df.mil.service.MessageService;

import javax.transaction.Transactional;
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
    @Transactional
    public List<MessageDto> findMessagesByDialogId(Long id) {
        List<Message> messages= messageRepository.findMessagesByDialogId(id);
        if(messages.isEmpty()) throw new DialogNotFoundException("Dialog with id " + id + " does not exist!");
        return messages.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public int deleteMessagesByUser(Long id) {
        return messageRepository.deleteByFromWhoIdOrToWhoId(id, id);
    }
}
