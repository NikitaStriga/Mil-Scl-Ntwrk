package q3df.mil.mapper.photo.pcl;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.pcl.PhotoCommentLikeSaveDto;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.exception.PhotoCommentNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.PhotoCommentRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class PhotoCommentLikeSaveMapper extends Mapper<PhotoCommentLike, PhotoCommentLikeSaveDto> {

    private final UserRepository userRepository;
    private final PhotoCommentRepository photoCommentRepository;

    public PhotoCommentLikeSaveMapper(UserRepository userRepository, PhotoCommentRepository photoCommentRepository) {
        super(PhotoCommentLike.class, PhotoCommentLikeSaveDto.class);
        this.userRepository = userRepository;
        this.photoCommentRepository = photoCommentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PhotoCommentLike.class, PhotoCommentLikeSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoCommentLikeSaveDto.class, PhotoCommentLike.class)
                .addMappings(m -> m.skip(PhotoCommentLike::setPhotoComment))
                .addMappings(m -> m.skip(PhotoCommentLike::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(PhotoCommentLike source, PhotoCommentLikeSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(PhotoCommentLikeSaveDto source, PhotoCommentLike destination) {
        destination.setPhotoComment(photoCommentRepository.findById(source.getPhotoCommentId())
                .orElseThrow(() -> new PhotoCommentNotFoundException("Photo comment with id " + source.getPhotoCommentId() + " doesn't exist!")));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + source.getUserId() + " doesn't exist!")));
    }
}
