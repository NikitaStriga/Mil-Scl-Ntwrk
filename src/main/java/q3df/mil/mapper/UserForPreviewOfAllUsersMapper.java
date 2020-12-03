package q3df.mil.mapper;

import org.springframework.stereotype.Component;
import q3df.mil.dto.UserForPreviewOfAllUsersDto;
import q3df.mil.entities.users_roles.User;

@Component
public class UserForPreviewOfAllUsersMapper extends Mapper<User, UserForPreviewOfAllUsersDto> {

    public UserForPreviewOfAllUsersMapper() {
        super(User.class, UserForPreviewOfAllUsersDto.class);
    }

}
