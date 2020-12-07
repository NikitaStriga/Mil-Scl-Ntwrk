package q3df.mil.mapper.photo;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.PhotoDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class PhotoMapper extends Mapper<Photo, PhotoDto> {

    private final UserRepository userRepository;

    public PhotoMapper(UserRepository userRepository) {
        super(Photo.class, PhotoDto.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Photo.class, PhotoDto.class)
                .addMappings(m -> m.skip(PhotoDto::setUserId))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoDto.class, Photo.class)
                .addMappings(m -> m.skip(Photo::setUser)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Photo source, PhotoDto destination) {
        destination.setUserId(source.getUser().getId());
    }

    @Override
    public void mapFromDtoToEntity(PhotoDto source, Photo destination) {
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }

}
