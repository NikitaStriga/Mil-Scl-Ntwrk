package q3df.mil.mapper.text.tc;

import org.springframework.stereotype.Component;
import q3df.mil.dto.text.tc.TextCommentSaveDto;
import q3df.mil.entities.text.TextComment;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.TextRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextCommentSaveMapper extends Mapper<TextComment, TextCommentSaveDto> {

    private final UserRepository userRepository;
    private final TextRepository textRepository;

    public TextCommentSaveMapper(UserRepository userRepository, TextRepository textRepository) {
        super(TextComment.class, TextCommentSaveDto.class);
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TextComment.class, TextCommentSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextCommentSaveDto.class, TextComment.class)
                .addMappings(m -> m.skip(TextComment::setText))
                .addMappings(m -> m.skip(TextComment::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextComment source, TextCommentSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(TextCommentSaveDto source, TextComment destination) {
        destination.setText(textRepository.findById(source.getTextId())
                .orElseThrow(() -> new TextNotFoundException("Text with id " + source.getTextId() + " doesn't exist!")));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + source.getUserId() + " doesn't exist!")));
    }
}
