package q3df.mil.security.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@PropertySource("classpath:messages.properties")
public class PasswordRecovery {

    @NotBlank(message = "{email.empty}")
    @NotNull(message = "{email.empty}")
    @NotEmpty(message = "{email.empty}")
    @Size(min=3,max = 50,message = "{email.size} {min}-{max} characters!")
    @Email(message = "{email.pattern}")
    private String email;


    @NotBlank(message = "{login.empty}")
    @NotNull(message = "{login.empty}")
    @NotEmpty(message = "{login.empty}")
    @Size(min=3,max = 50,message = "{login.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{login.pattern} {regexp}")
    private String login;

}
