package q3df.mil.mapper.photo.p;

import org.springframework.stereotype.Component;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

import static q3df.mil.exception.ExceptionConstants.USER_NF;

@Component
public class PhotoSaveMapper extends Mapper<Photo, PhotoSaveDto> {

    private final UserRepository userRepository;

    public PhotoSaveMapper(UserRepository userRepository) {
        super(Photo.class, PhotoSaveDto.class);
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Photo.class, PhotoSaveDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(PhotoSaveDto.class, Photo.class)
                .addMappings(m -> m.skip(Photo::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Photo source, PhotoSaveDto destination) {
        //
    }

    @Override
    public void mapFromDtoToEntity(PhotoSaveDto source, Photo destination) {
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NF + source.getUserId())));
    }
}
