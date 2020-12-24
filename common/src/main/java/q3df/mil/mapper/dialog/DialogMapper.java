package q3df.mil.mapper.dialog;

import org.springframework.stereotype.Component;
import q3df.mil.dto.dialog.DialogDto;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.mapper.Mapper;
import q3df.mil.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@Component
public class DialogMapper extends Mapper<Dialog, DialogDto> {

    private final UserRepository userRepository;

    public DialogMapper(UserRepository userRepository) {
        super(Dialog.class, DialogDto.class);
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Dialog.class, DialogDto.class)
                .addMappings(m -> m.skip(DialogDto::setFirstUserId))
                .addMappings(m -> m.skip(DialogDto::setSecondUserId))
                .addMappings(m -> m.skip(DialogDto::setFirstUserName))
                .addMappings(m -> m.skip(DialogDto::setFirstUserSurname))
                .addMappings(m -> m.skip(DialogDto::setSecondUserName))
                .addMappings(m -> m.skip(DialogDto::setSecondUserSurname))
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(DialogDto.class, Dialog.class)
                .addMappings(m -> m.skip(Dialog::setUsers)).setPostConverter(toEntityConverter());
    }


    @Override
    public void mapFromEntityToDto(Dialog source, DialogDto destination) {
        destination.setFirstUserId(source.getUsers().get(0).getId());
        destination.setFirstUserName(source.getUsers().get(0).getFirstName());
        destination.setFirstUserSurname(source.getUsers().get(0).getLastName());
        destination.setSecondUserId(source.getUsers().get(1).getId());
        destination.setSecondUserName(source.getUsers().get(1).getFirstName());
        destination.setSecondUserSurname(source.getUsers().get(1).getLastName());
    }



    @Override
    public void mapFromDtoToEntity(DialogDto source, Dialog destination) {
        destination.setUsers(Arrays.asList(userRepository.findById(source.getFirstUserId()).get(),
                userRepository.findById(source.getSecondUserId()).get()));
    }
}
