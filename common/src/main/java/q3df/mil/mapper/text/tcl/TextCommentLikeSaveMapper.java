package q3df.mil.mapper.text.tcl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.tcl.TextCommentLikeDto;
import q3df.mil.dto.text.tcl.TextCommentLikeSaveDto;
import q3df.mil.entities.text.TextCommentLike;
import q3df.mil.exception.TextCommentNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.TextCommentRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

import static q3df.mil.entities.text.TextCommentLike_.TEXT_COMMENT;
import static q3df.mil.exception.ExceptionConstants.USER_NF;

@Component
public class TextCommentLikeSaveMapper extends Mapper<TextCommentLike, TextCommentLikeSaveDto> {

    private final TextCommentRepository textCommentRepository;
    private final UserRepository userRepository;

    @Autowired
    public TextCommentLikeSaveMapper(TextCommentRepository textCommentRepository, UserRepository userRepository) {
        super(TextCommentLike.class, TextCommentLikeSaveDto.class);
        this.textCommentRepository = textCommentRepository;
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(TextCommentLike.class, TextCommentLikeSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextCommentLikeSaveDto.class, TextCommentLike.class)
                .addMappings(m -> m.skip(TextCommentLike::setTextComment))
                .addMappings(m -> m.skip(TextCommentLike::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(TextCommentLike source, TextCommentLikeSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(TextCommentLikeSaveDto source, TextCommentLike destination) {
        destination.setTextComment(textCommentRepository.findById(source.getTextCommentId())
                .orElseThrow(() -> new TextCommentNotFoundException(TEXT_COMMENT + source.getTextCommentId())));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NF + source.getUserId())));
    }
}
