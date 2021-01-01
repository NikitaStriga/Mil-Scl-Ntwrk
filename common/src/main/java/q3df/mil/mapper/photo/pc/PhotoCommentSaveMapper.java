package q3df.mil.mapper.photo.pc;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.pc.PhotoCommentSaveDto;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

import static q3df.mil.exception.ExceptionConstants.PHOTO_NF;
import static q3df.mil.exception.ExceptionConstants.USER_NF;

@Component
public class PhotoCommentSaveMapper extends Mapper<PhotoComment, PhotoCommentSaveDto> {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;

    public PhotoCommentSaveMapper(UserRepository userRepository, PhotoRepository photoRepository) {
        super(PhotoComment.class, PhotoCommentSaveDto.class);
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
    }



    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PhotoComment.class, PhotoCommentSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoCommentSaveDto.class, PhotoComment.class)
                .addMappings(m -> m.skip(PhotoComment::setPhoto))
                .addMappings(m -> m.skip(PhotoComment::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(PhotoComment source, PhotoCommentSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(PhotoCommentSaveDto source, PhotoComment destination) {
        destination.setPhoto(photoRepository.findById(source.getPhotoId())
                .orElseThrow(() -> new PhotoNotFoundException(PHOTO_NF + source.getPhotoId())));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NF + source.getUserId())));
    }


}
