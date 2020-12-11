package q3df.mil.mapper.message;

import org.springframework.stereotype.Component;
import q3df.mil.dto.message.MessageDto;
import q3df.mil.dto.message.MessageSaveDto;
import q3df.mil.entities.message.Message;
import q3df.mil.exception.MessageNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.DialogRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class MessageSaveMapper extends Mapper<Message, MessageSaveDto> {

    private final UserRepository userRepository;
    private final DialogRepository dialogRepository;

    public MessageSaveMapper(UserRepository userRepository, DialogRepository dialogRepository) {
        super(Message.class, MessageSaveDto.class);
        this.userRepository = userRepository;
        this.dialogRepository = dialogRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Message.class, MessageSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(MessageSaveDto.class, Message.class)
                .addMappings(m -> m.skip(Message::setDialog))
                .addMappings(m -> m.skip(Message::setFromWho))
                .addMappings(m -> m.skip(Message::setToWho))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Message source, MessageSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(MessageSaveDto source, Message destination) {
        destination.setDialog(dialogRepository.findById(source.getDialogId()).orElse(null));
        destination.setFromWho(userRepository.findById(source.getFromWhoId())
                .orElseThrow( () -> new UserNotFoundException("User with id " + source.getFromWhoId() + " doesn't exist!1")));
        destination.setToWho(userRepository.findById(source.getToWhoId())
                .orElseThrow( () -> new UserNotFoundException("User with id " + source.getToWhoId() + " doesn't exist!1")));
    }
}
