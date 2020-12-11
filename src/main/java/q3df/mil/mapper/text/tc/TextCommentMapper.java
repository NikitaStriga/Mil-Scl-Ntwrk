package q3df.mil.mapper.text.tc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.tc.TextCommentDto;
import q3df.mil.entities.text.TextComment;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.TextRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextCommentMapper extends Mapper<TextComment, TextCommentDto> {

    private final UserRepository userRepository;
    private final TextRepository textRepository;

    @Autowired
    public TextCommentMapper(UserRepository userRepository, TextRepository textRepository){
        super(TextComment.class,TextCommentDto.class);
        this.userRepository = userRepository;
        this.textRepository = textRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TextComment.class, TextCommentDto.class)
                .addMappings(m -> m.skip(TextCommentDto::setTextId))
                .addMappings( m -> m.skip(TextCommentDto::setUserId))
                .addMappings( m -> m.skip(TextCommentDto::setUsername))
                .addMappings( m -> m.skip(TextCommentDto::setSurname))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextCommentDto.class, TextComment.class)
                .addMappings(m -> m.skip(TextComment::setText))
                .addMappings(m -> m.skip(TextComment::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextComment source, TextCommentDto destination) {
        destination.setUserId(source.getUser().getId());
        destination.setTextId(source.getText().getId());
        destination.setUsername(source.getUser().getFirstName());
        destination.setSurname(source.getUser().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(TextCommentDto source, TextComment destination) {
        destination.setText(textRepository.findById(source.getTextId()).orElse(null));
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }


}
