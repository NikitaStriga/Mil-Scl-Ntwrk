package q3df.mil.mapper.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import q3df.mil.dto.user.UserRegistrationDto;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.user.User;
import q3df.mil.mapper.Mapper;

import javax.annotation.PostConstruct;

@Component
public class UserRegistrationMapper extends Mapper<User, UserRegistrationDto> {

//    private final PasswordEncoder passwordEncoder;

    public UserRegistrationMapper() {
        super(User.class, UserRegistrationDto.class);
//        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserRegistrationDto.class)
                .addMappings(m -> m.skip(UserRegistrationDto::setGender))
                .addMappings(m -> m.skip(UserRegistrationDto::setPassword))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(UserRegistrationDto.class, User.class)
                .addMappings(m -> m.skip(User::setGender))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(User source, UserRegistrationDto destination) {
        destination.setGender(source.getGender().name());
    }

    @Override
    public void mapFromDtoToEntity(UserRegistrationDto source, User destination) {
        destination.setGender(Gender.valueOf(source.getGender().toUpperCase()));
//        destination.setPassword(passwordEncoder.encode(source.getPassword()));
    }
}
