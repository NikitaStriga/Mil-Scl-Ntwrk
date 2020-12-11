package q3df.mil.mapper.text.t;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.t.TextSaveDto;
import q3df.mil.entities.text.Text;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextSaveMapper extends Mapper<Text, TextSaveDto> {


    private final UserRepository userRepository;

    @Autowired
    public TextSaveMapper(UserRepository userRepository) {
        super(Text.class, TextSaveDto.class);
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Text.class, TextSaveDto.class)
                .addMappings(m -> m.skip(TextSaveDto::setUserId))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextSaveDto.class, Text.class)
                .addMappings(m -> m.skip(Text::setUser))
                .setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Text source, TextSaveDto destination) {
        destination.setUserId(source.getUser().getId());
    }


    @Override
    public void mapFromDtoToEntity(TextSaveDto source, Text destination) {
        destination.setUser(userRepository.findById(source.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id " + source.getUserId() + " doesn't exist!")));
    }


}
