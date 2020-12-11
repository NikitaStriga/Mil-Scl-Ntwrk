package q3df.mil.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {

    private String login;

    private String password;

    private String newPassword;


}
