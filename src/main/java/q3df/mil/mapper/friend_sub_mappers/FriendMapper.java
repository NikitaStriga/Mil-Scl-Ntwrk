package q3df.mil.mapper.friend_sub_mappers;

import org.springframework.stereotype.Component;
import q3df.mil.dto.friends_subs_dto.FriendDto;
import q3df.mil.entities.users_roles.User;
import q3df.mil.mapper.Mapper;

import javax.annotation.PostConstruct;

@Component
public class FriendMapper extends Mapper<User, FriendDto> {


    public FriendMapper() {
        super(User.class, FriendDto.class);
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, FriendDto.class)
                .addMappings(m -> m.skip(FriendDto::setUserId))
                .addMappings(m -> m.skip(FriendDto::setName))
                .addMappings(m -> m.skip(FriendDto::setSurname))
                .setPostConverter(toDtoConverter());
//        modelMapper.createTypeMap(FriendDto.class, User.class)
//                .addMappings(m -> m.skip(Text::setUser)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(User source, FriendDto destination) {
        destination.setUserId(source.getId());
        destination.setName(source.getFirstName());
        destination.setSurname(source.getLastName());
    }

    @Override
    public void mapFromDtoToEntity(FriendDto source, User destination) {
//        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
