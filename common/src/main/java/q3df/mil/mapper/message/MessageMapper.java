package q3df.mil.mapper.message;

import org.springframework.stereotype.Component;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.entities.message.Message;
import q3df.mil.exception.MessageNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.DialogRepository;
import q3df.mil.repository.MessageRepository;

import javax.annotation.PostConstruct;


@Component
public class MessageMapper extends Mapper<Message, MessageDto> {


    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;

    public MessageMapper(DialogRepository dialogRepository, MessageRepository messageRepository) {
        super(Message.class, MessageDto.class);
        this.dialogRepository = dialogRepository;
        this.messageRepository = messageRepository;
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMappings(m -> m.skip(MessageDto::setDialogId))
                .addMappings(m -> m.skip(MessageDto::setFromWhoId))
                .addMappings(m -> m.skip(MessageDto::setFromWhoName))
                .addMappings(m -> m.skip(MessageDto::setFromWhoSurname))
                .addMappings(m -> m.skip(MessageDto::setToWhoId))
                .addMappings(m -> m.skip(MessageDto::setToWhoName))
                .addMappings(m -> m.skip(MessageDto::setFromWhoSurname))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(MessageDto.class, Message.class)
                .addMappings(m -> m.skip(Message::setDialog)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Message source, MessageDto destination) {
        destination.setDialogId(source.getDialog().getId());
        destination.setFromWhoId(source.getFromWho().getId());
        destination.setFromWhoName(source.getFromWho().getFirstName());
        destination.setFromWhoSurname(source.getFromWho().getLastName());
        destination.setToWhoId(source.getToWho().getId());
        destination.setToWhoName(source.getToWho().getFirstName());
        destination.setToWhoSurname(source.getToWho().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(MessageDto source, Message destination) {
        destination.setDialog(dialogRepository.findById(source.getDialogId()).
                orElse(null));
        Message message = messageRepository.findById(source.getId()).
                orElseThrow(() -> new MessageNotFoundException("Cant find message!"));
        destination.setFromWho(message.getFromWho());
        destination.setFromWho(message.getToWho());
    }


}
