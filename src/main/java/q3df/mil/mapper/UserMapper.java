package q3df.mil.mapper;

import org.springframework.stereotype.Component;
import q3df.mil.dto.UserDto;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.users_roles.User;

import javax.annotation.PostConstruct;


@Component
public class UserMapper extends Mapper<User,UserDto> {


    public UserMapper() {
        super(User.class, UserDto.class);
    }



    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> m.skip(UserDto::setGender))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserDto.class, User.class)
                .addMappings(m -> m.skip(User::setGender)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(User source, UserDto destination) {
        destination.setGender(source.getGender().name());
    }

    @Override
    public void mapFromDtoToEntity(UserDto source, User destination) {
        destination.setGender(Gender.valueOf(source.getGender().toUpperCase()));
    }
}
