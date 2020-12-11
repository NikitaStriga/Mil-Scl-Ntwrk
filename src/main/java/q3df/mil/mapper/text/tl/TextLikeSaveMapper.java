package q3df.mil.mapper.text.tl;

import org.springframework.stereotype.Component;
import q3df.mil.dto.text.tl.TextLikeSaveDto;
import q3df.mil.entities.text.TextLike;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.TextRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextLikeSaveMapper extends Mapper<TextLike, TextLikeSaveDto> {


    private final UserRepository userRepository;
    private final TextRepository textRepository;

    public TextLikeSaveMapper(UserRepository userRepository, TextRepository textRepository) {
        super(TextLike.class, TextLikeSaveDto.class);
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TextLike.class, TextLikeSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextLikeSaveDto.class, TextLike.class)
                .addMappings(m -> m.skip(TextLike::setTextId))
                .addMappings(m -> m.skip(TextLike::setUserId))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextLike source, TextLikeSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(TextLikeSaveDto source, TextLike destination) {
        destination.setTextId(textRepository.findById(source.getTextId())
                .orElseThrow( () -> new TextNotFoundException("Text with id " + source.getTextId() + "doesn't exist!")));
        destination.setUserId(userRepository.findById(source.getUserId())
                .orElseThrow( () -> new UserNotFoundException("User with id " + source.getUserId() + "doesn't exist!")));
    }
}
