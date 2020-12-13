package q3df.mil.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String username;

    private String token;

}
