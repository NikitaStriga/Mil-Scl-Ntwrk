package q3df.mil.security.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    @ApiModelProperty(position = 1)
    private String username;

    @ApiModelProperty(position = 2)
    private String token;

}
