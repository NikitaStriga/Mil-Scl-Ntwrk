package q3df.mil.mapper.user;

import org.springframework.stereotype.Component;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.entities.user.User;
import q3df.mil.mapper.Mapper;

@Component
public class UserPreviewMapper extends Mapper<User, UserPreview> {

    public UserPreviewMapper() {
        super(User.class, UserPreview.class);
    }

}
