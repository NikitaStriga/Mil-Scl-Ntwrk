package q3df.mil.mapper.photo.pl;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.pl.PhotoLikeDto;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.PhotoRepository;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class PhotoLikeMapper extends Mapper<PhotoLike, PhotoLikeDto> {


    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    public PhotoLikeMapper(PhotoRepository photoRepository, UserRepository userRepository) {
        super(PhotoLike.class, PhotoLikeDto.class);
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(PhotoLike.class, PhotoLikeDto.class)
                .addMappings(m -> m.skip(PhotoLikeDto::setPhotoId))
                .addMappings(m -> m.skip(PhotoLikeDto::setUserId))
                .addMappings(m -> m.skip(PhotoLikeDto::setFirstName))
                .addMappings(m -> m.skip(PhotoLikeDto::setLastName))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoLikeDto.class, PhotoLike.class)
                .addMappings(m -> m.skip(PhotoLike::setPhoto))
                .addMappings(m -> m.skip(PhotoLike::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(PhotoLike source, PhotoLikeDto destination) {
        destination.setPhotoId(source.getPhoto().getId());
        destination.setUserId(source.getUser().getId());
        destination.setFirstName(source.getUser().getFirstName());
        destination.setLastName(source.getUser().getLastName());
    }

    @Override
    public void mapFromDtoToEntity(PhotoLikeDto source, PhotoLike destination) {
        destination.setPhoto(photoRepository.findById(source.getPhotoId()).orElse(null));
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
