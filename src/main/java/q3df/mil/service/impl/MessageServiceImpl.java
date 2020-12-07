package q3df.mil.service.impl;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.entities.message.Message;
import q3df.mil.exception.DialogNotFoundException;
import q3df.mil.exception.MessageNotFoundException;
import q3df.mil.exception.UserNotFoundException;
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
    public void deleteById(Long id) {
        try{
            messageRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new MessageNotFoundException("Message with id " + id + " doesn't exist!");
        }
    }

    @Override
    public MessageDto saveMessage(MessageDto messageDto) {
        Message message = messageMapper.fromDto(messageDto);
        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }
}
