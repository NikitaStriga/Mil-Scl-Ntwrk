package q3df.mil.mapper.text.tl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.tl.TextLikeDto;
import q3df.mil.entities.text.TextLike;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.TextRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextLikeMapper extends Mapper<TextLike, TextLikeDto> {

    private final TextRepository textRepository;
    private final UserRepository userRepository;

    @Autowired
    public TextLikeMapper(TextRepository textRepository, UserRepository userRepository){
        super(TextLike.class,TextLikeDto.class);
        this.textRepository = textRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TextLike.class, TextLikeDto.class)
                .addMappings(m -> m.skip(TextLikeDto::setTextId))
                .addMappings( m -> m.skip(TextLikeDto::setUserId))
                .addMappings( m -> m.skip(TextLikeDto::setUsername))
                .addMappings( m -> m.skip(TextLikeDto::setSurname))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextLikeDto.class, TextLike.class)
                .addMappings(m -> m.skip(TextLike::setTextId))
                .addMappings(m -> m.skip(TextLike::setUserId))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextLike source, TextLikeDto destination) {
        destination.setUserId(source.getTextId().getId());
        destination.setTextId(source.getUserId().getId());
        destination.setUsername(source.getUserId().getFirstName());
        destination.setSurname(source.getUserId().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(TextLikeDto source, TextLike destination) {
        destination.setTextId(textRepository.findById(source.getTextId())
                .orElseThrow( () -> new TextNotFoundException("Text with id " + source.getTextId() + "doesn't exist!")));
        destination.setUserId(userRepository.findById(source.getUserId())
                .orElseThrow( () -> new UserNotFoundException("User with id " + source.getUserId() + "doesn't exist!")));
    }
}
