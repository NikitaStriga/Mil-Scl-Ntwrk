package q3df.mil.mapper.photo.pl;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.pl.PhotoLikeSaveDto;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.exception.PhotoNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

import static q3df.mil.exception.ExceptionConstants.PHOTO_NF;
import static q3df.mil.exception.ExceptionConstants.USER_NF;

@Component
public class PhotoLikeSaveMapper extends Mapper<PhotoLike, PhotoLikeSaveDto> {


    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public PhotoLikeSaveMapper(PhotoRepository photoRepository, UserRepository userRepository) {
        super(PhotoLike.class, PhotoLikeSaveDto.class);
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PhotoLike.class, PhotoLikeSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoLikeSaveDto.class, PhotoLike.class)
                .addMappings(m -> m.skip(PhotoLike::setPhoto))
                .addMappings(m -> m.skip(PhotoLike::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(PhotoLike source, PhotoLikeSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(PhotoLikeSaveDto source, PhotoLike destination) {
        destination.setPhoto(photoRepository.findById(source.getPhotoId())
                .orElseThrow(() -> new PhotoNotFoundException(PHOTO_NF + source.getPhotoId())));
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NF + source.getUserId())));
    }
}
