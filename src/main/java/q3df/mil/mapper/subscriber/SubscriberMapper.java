package q3df.mil.mapper.subscriber;

import org.springframework.stereotype.Component;
import q3df.mil.dto.subscriber.SubscriberDto;
import q3df.mil.entities.user.User;
import q3df.mil.mapper.Mapper;

import javax.annotation.PostConstruct;

@Component
public class SubscriberMapper extends Mapper<User, SubscriberDto> {


    public SubscriberMapper(){
        super(User.class,SubscriberDto.class);
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, SubscriberDto.class)
                .addMappings(m -> m.skip(SubscriberDto::setUserId))
                .addMappings(m -> m.skip(SubscriberDto::setName))
                .addMappings(m -> m.skip(SubscriberDto::setSurname))
                .setPostConverter(toDtoConverter());
//        modelMapper.createTypeMap(FriendDto.class, User.class)
//                .addMappings(m -> m.skip(Text::setUser)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(User source, SubscriberDto destination) {
        destination.setUserId(source.getId());
        destination.setName(source.getFirstName());
        destination.setSurname(source.getLastName());
    }

    @Override
    public void mapFromDtoToEntity(SubscriberDto source, User destination) {
//        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
