package q3df.mil.mapper.user;

import org.springframework.stereotype.Component;
import q3df.mil.dto.user.UserForPreviewOfAllUsersDto;
import q3df.mil.entities.user.User;
import q3df.mil.mapper.Mapper;

@Component
public class UserForPreviewOfAllUsersMapper extends Mapper<User, UserForPreviewOfAllUsersDto> {

    public UserForPreviewOfAllUsersMapper() {
        super(User.class, UserForPreviewOfAllUsersDto.class);
    }

}
