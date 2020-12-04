package q3df.mil.mapper.text;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import q3df.mil.dto.text.TextDto;
import q3df.mil.entities.text.Text;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class TextMapper extends Mapper<Text, TextDto> {

    private final UserRepository userRepository;

    @Autowired
    public TextMapper(UserRepository userRepository) {
        super(Text.class, TextDto.class);
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Text.class, TextDto.class)
                .addMappings(m -> m.skip(TextDto::setUserId))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(TextDto.class, Text.class)
                .addMappings(m -> m.skip(Text::setUser)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Text source, TextDto destination) {
        destination.setUserId(source.getUser().getId());
    }

    @Override
    public void mapFromDtoToEntity(TextDto source, Text destination) {
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
