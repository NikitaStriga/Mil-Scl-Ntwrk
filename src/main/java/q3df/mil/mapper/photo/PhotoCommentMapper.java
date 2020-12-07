package q3df.mil.mapper.photo;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.PhotoCommentDto;
import q3df.mil.dto.photo.PhotoDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;


@Component
public class PhotoCommentMapper extends Mapper<PhotoComment, PhotoCommentDto> {

    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public PhotoCommentMapper(PhotoRepository photoRepository, UserRepository userRepository) {
        super(PhotoComment.class, PhotoCommentDto.class);
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PhotoComment.class, PhotoCommentDto.class)
                .addMappings(m -> m.skip(PhotoCommentDto::setPhotoId))
                .addMappings(m -> m.skip(PhotoCommentDto::setUserId))
                .addMappings(m -> m.skip(PhotoCommentDto::setFirstName))
                .addMappings(m -> m.skip(PhotoCommentDto::setLastName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoCommentDto.class, PhotoComment.class)
                .addMappings(m -> m.skip(PhotoComment::setPhoto))
                .addMappings(m -> m.skip(PhotoComment::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(PhotoComment source, PhotoCommentDto destination) {
        destination.setPhotoId(source.getPhoto().getId());
        destination.setUserId(source.getUser().getId());
        destination.setFirstName(source.getUser().getFirstName());
        destination.setLastName(source.getUser().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(PhotoCommentDto source, PhotoComment destination) {
        destination.setPhoto(photoRepository.findById(source.getPhotoId()).orElse(null));
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }

}
