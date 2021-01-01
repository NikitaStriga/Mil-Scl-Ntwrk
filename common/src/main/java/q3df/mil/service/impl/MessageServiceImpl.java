package q3df.mil.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.dto.message.MessageSaveDto;
import q3df.mil.dto.message.MessageUpdateDto;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.entities.message.Message;
import q3df.mil.exception.CustomException;
import q3df.mil.exception.MessageNotFoundException;
import q3df.mil.mapper.message.MessageMapper;
import q3df.mil.mapper.message.MessageSaveMapper;
import q3df.mil.repository.MessageRepository;
import q3df.mil.service.MessageService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static q3df.mil.exception.ExceptionConstants.MESSAGE_NF;

@Service
public class MessageServiceImpl implements MessageService {


    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageSaveMapper messageSaveMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper, MessageSaveMapper messageSaveMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.messageSaveMapper = messageSaveMapper;
    }


    /**
     * find all messages by dialog id
     * @param id dialog id
     * @return founded messages
     */
    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<MessageDto> findMessagesByDialogId(Long id) {
        return messageRepository
                .findMessagesByDialogIdOrderByCreatedDesc(id)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * save new messages
     * if the dialog does not exist between users at the moment, then it will be installed automatically
     * @param messageSaveDto new message
     * @return saved message
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public MessageDto saveMessage(MessageSaveDto messageSaveDto) {
//        if (messageSaveDto.getFromWhoId().equals(messageSaveDto.getToWhoId())) {
//            throw new CustomException("You cannot write to yourself");
//        }
//        Message message = messageSaveMapper.fromDto(messageSaveDto);
//        if (message.getDialog()==null) {
//            Dialog dialog = new Dialog();
//            message.setDialog(dialog);
//        }else {
//            Long dialogId = message.getDialog().getId();
//            Message checkMessage = message.getDialog().getMessages().get(0);
//            Long fromWhoId = checkMessage.getFromWho().getId();
//            Long toWhoId = checkMessage.getToWho().getId();
//            if(!fromWhoId.equals(message.getFromWho().getId())){
//                throw new CustomException("User with id " + fromWhoId + " not belong to dialog with id " + dialogId);
//            }
//            if(!toWhoId.equals(message.getToWho().getId())){
//                throw new CustomException("User with id " + toWhoId + " not belong to dialog with id " + dialogId);
//            }
//        }
//        Message savedMessage = messageRepository.save(message);
//        return messageMapper.toDto(savedMessage);
        if (messageSaveDto.getFromWhoId().equals(messageSaveDto.getToWhoId())) {
            throw new CustomException("You cannot write to yourself");
        }
        Message message = messageSaveMapper.fromDto(messageSaveDto);
        if (message.getDialog()==null) {
            Dialog dialog = new Dialog();
            message.setDialog(dialog);
        }
        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }


    /**
     * edit message
     * @param messageUpdateDto message
     * @return edited message
     */
    @Override
    @Transactional
    public MessageDto updateMessage(MessageUpdateDto messageUpdateDto) {
        Message message =
                messageRepository
                .findById(messageUpdateDto.getId())
                .orElseThrow( () -> new MessageNotFoundException(MESSAGE_NF + messageUpdateDto.getId()));
        message.setText(messageUpdateDto.getText());
        return messageMapper.toDto(message);
    }


    /**
     * delete message by id
     * @param id message id
     */
    @Override
    public void deleteById(Long id) {
        try{
            messageRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new MessageNotFoundException(MESSAGE_NF+ id);
        }
    }
}
