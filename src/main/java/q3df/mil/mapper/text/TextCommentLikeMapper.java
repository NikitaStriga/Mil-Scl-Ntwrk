package q3df.mil.mapper.text;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.TextCommentLikeDto;
import q3df.mil.entities.text.TextCommentLike;
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
                .addMappings(m -> m.skip(TextCommentLike::setTextCommentId))
//                .addMappings(m -> m.skip(TextCommentLike::setUserId))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextCommentLike source, TextCommentLikeDto destination) {
        destination.setTextCommentId(source.getTextCommentId().getId());
        destination.setUserId(source.getUserId().getId());
        destination.setUsername(source.getUserId().getFirstName());
        destination.setSurname(source.getUserId().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(TextCommentLikeDto source, TextCommentLike destination) {
        destination.setTextCommentId(textCommentRepository.findById(source.getTextCommentId()).orElse(null));
        destination.setUserId(userRepository.findById(source.getUserId()).orElse(null));
    }


}
