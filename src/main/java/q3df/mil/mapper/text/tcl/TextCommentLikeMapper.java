package q3df.mil.mapper.text.tcl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.tcl.TextCommentLikeDto;
import q3df.mil.entities.text.TextCommentLike;
import q3df.mil.exception.TextCommentNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.TextCommentRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextCommentLikeMapper extends Mapper<TextCommentLike, TextCommentLikeDto> {

    private final TextCommentRepository textCommentRepository;
    private final UserRepository userRepository;

    @Autowired
    public TextCommentLikeMapper(TextCommentRepository textCommentRepository, UserRepository userRepository){
        super(TextCommentLike.class,TextCommentLikeDto.class);
        this.textCommentRepository = textCommentRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TextCommentLike.class, TextCommentLikeDto.class)
                .addMappings(m -> m.skip(TextCommentLikeDto::setTextCommentId))
                .addMappings( m -> m.skip(TextCommentLikeDto::setUserId))
                .addMappings( m -> m.skip(TextCommentLikeDto::setUsername))
                .addMappings( m -> m.skip(TextCommentLikeDto::setSurname))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextCommentLikeDto.class, TextCommentLike.class)
                .addMappings(m -> m.skip(TextCommentLike::setTextComment))
//                .addMappings(m -> m.skip(TextCommentLike::setUserId))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextCommentLike source, TextCommentLikeDto destination) {
        destination.setTextCommentId(source.getTextComment().getId());
        destination.setUserId(source.getUser().getId());
        destination.setUsername(source.getUser().getFirstName());
        destination.setSurname(source.getUser().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(TextCommentLikeDto source, TextCommentLike destination) {
        destination.setTextComment(textCommentRepository.findById(source.getTextCommentId())
                .orElseThrow(() -> new TextCommentNotFoundException("Text comment with id " + source.getTextCommentId() + " doesn't exist!")));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + source.getUserId() + " doesn't exist!")));
    }


}
