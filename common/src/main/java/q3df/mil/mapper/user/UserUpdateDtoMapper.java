package q3df.mil.mapper.user;

import org.springframework.stereotype.Component;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.dto.user.UserUpdateDto;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.user.User;
import q3df.mil.mapper.Mapper;

import javax.annotation.PostConstruct;

@Component
public class UserUpdateDtoMapper extends Mapper<User, UserUpdateDto> {

    public UserUpdateDtoMapper() {
        super(User.class, UserUpdateDto.class);
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserUpdateDto.class)
                .addMappings(m -> m.skip(UserUpdateDto::setGender))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserUpdateDto.class, User.class)
                .addMappings(m -> m.skip(User::setGender))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(User source, UserUpdateDto destination) {
        destination.setGender(source.getGender().name());
    }

    @Override
    public void mapFromDtoToEntity(UserUpdateDto source, User destination) {
        destination.setGender(Gender.valueOf(source.getGender().toUpperCase()));

    }

}
