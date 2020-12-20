package q3df.mil.security.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@PropertySource("classpath:messages.properties")
public class ChangePasswordRequest {

    @NotBlank(message = "{login.empty}")
    @NotNull(message = "{login.empty}")
    @NotEmpty(message = "{login.empty}")
    @Size(min=3,max = 50,message = "{login.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{login.pattern} {regexp}")
    private String login;

    @NotBlank(message = "{password.empty}")
    @NotNull(message = "{password.empty}")
    @NotEmpty(message = "{password.empty}")
    @Size(min=3,max = 50,message = "{password.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{password.pattern} {regexp}")
    private String password;

    @ApiModelProperty(position = 3)
    @NotBlank(message = "{password.empty}")
    @NotNull(message = "{password.empty}")
    @NotEmpty(message = "{password.empty}")
    @Size(min=3,max = 50,message = "{password.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{password.pattern} {regexp}")
    private String newPassword;


}
