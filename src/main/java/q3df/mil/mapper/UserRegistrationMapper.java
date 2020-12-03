package q3df.mil.mapper;

import org.springframework.stereotype.Component;
import q3df.mil.dto.UserRegistrationDto;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.users_roles.User;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class UserRegistrationMapper extends Mapper<User, UserRegistrationDto> {

    public UserRegistrationMapper() {
        super(User.class, UserRegistrationDto.class);
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserRegistrationDto.class)
                .addMappings(m -> m.skip(UserRegistrationDto::setGender))
                .addMappings(m -> m.skip(UserRegistrationDto::setBirthday))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserRegistrationDto.class, User.class)
                .addMappings(m -> m.skip(User::setGender))
                .addMappings(m -> m.skip(User::setBirthday))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(User source, UserRegistrationDto destination) {
        destination.setGender(source.getGender().name());
//        destination.setBirthday(source.getBirthday().toString());
    }

    @Override
    public void mapFromDtoToEntity(UserRegistrationDto source, User destination) {
        destination.setGender(Gender.valueOf(source.getGender().toUpperCase()));
//        destination.setBirthday(LocalDate.parse(source.getBirthday()));
    }
}
