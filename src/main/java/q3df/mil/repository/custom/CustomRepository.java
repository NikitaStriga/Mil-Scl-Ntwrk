package q3df.mil.repository.custom;

import org.springframework.stereotype.Repository;
import q3df.mil.dto.user.UserPreview;

import java.util.List;

@Repository
public interface CustomRepository {

    public List<?> showCountOfUserByCityAndCountry();

    public List<UserPreview> showUsersByParams(String... params);

}
