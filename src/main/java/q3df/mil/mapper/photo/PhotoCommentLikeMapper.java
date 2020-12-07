package q3df.mil.mapper.photo;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.PhotoCommentLikeDto;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.PhotoCommentRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;


@Component
public class PhotoCommentLikeMapper extends Mapper<PhotoCommentLike, PhotoCommentLikeDto> {

    private final PhotoCommentRepository photoCommentRepository;
    private final UserRepository userRepository;

    public PhotoCommentLikeMapper(PhotoCommentRepository photoCommentRepository, UserRepository userRepository) {
        super(PhotoCommentLike.class, PhotoCommentLikeDto.class);
        this.photoCommentRepository = photoCommentRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PhotoCommentLike.class, PhotoCommentLikeDto.class)
                .addMappings(m -> m.skip(PhotoCommentLikeDto::setPhotoCommentId))
                .addMappings(m -> m.skip(PhotoCommentLikeDto::setUserId))
                .addMappings(m -> m.skip(PhotoCommentLikeDto::setFirstName))
                .addMappings(m -> m.skip(PhotoCommentLikeDto::setLastName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoCommentLikeDto.class, PhotoCommentLike.class)
                .addMappings(m -> m.skip(PhotoCommentLike::setPhotoComment))
                .addMappings(m -> m.skip(PhotoCommentLike::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(PhotoCommentLike source, PhotoCommentLikeDto destination) {
        destination.setPhotoCommentId(source.getId());
        destination.setUserId(source.getUser().getId());
        destination.setFirstName(source.getUser().getFirstName());
        destination.setLastName(source.getUser().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(PhotoCommentLikeDto source, PhotoCommentLike destination) {
        destination.setPhotoComment(photoCommentRepository.findById(source.getPhotoCommentId()).orElse(null));
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
