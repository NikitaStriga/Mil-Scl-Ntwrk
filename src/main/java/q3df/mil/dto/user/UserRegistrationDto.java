package q3df.mil.dto.user;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.validators.ValidDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class UserRegistrationDto {


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

    @NotBlank(message = "{password.empty}")
    @NotNull(message = "{password.empty}")
    @NotEmpty(message = "{password.empty}")
    @Size(min=3,max = 50,message = "{password.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{password.pattern} {regexp}")
    private String password;

    @NotBlank(message = "{firstName.empty}")
    @NotNull(message = "{firstName.empty}")
    @NotEmpty(message = "{firstName.empty}")
    @Size(min=3,max = 50,message = "{firstName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$",message = "{firstName.pattern} {regexp}")
    private String firstName;

    @NotBlank(message = "{lastName.empty}")
    @NotNull(message = "{lastName.empty}")
    @NotEmpty(message = "{lastName.empty}")
    @Size(min=3,max = 50,message = "{lastName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$",
            message = "{lastName.pattern} {regexp}")
    private String lastName;

    @NotBlank(message = "{gender.empty}")
    @NotNull(message = "{gender.empty}")
    @NotEmpty(message = "{gender.empty}")
    @Pattern(regexp = "(?i)male|female",
            message = "{gender.pattern} {regexp}")
    private String gender;

    @NotBlank(message = "{country.empty}")
    @NotNull(message = "{country.empty}")
    @NotEmpty(message = "{country.empty}")
    @Size(min=3,max = 50,
            message = "{country.size} {min}-{max} characters!")
    private String country;

    @NotBlank(message = "{city.empty}")
    @NotNull(message = "{city.empty}")
    @NotEmpty(message = "{city.empty}")
    @Size(min=3,max = 50,message = "{city.size} {min}-{max} characters!")
    private String city;


    @ValidDate(afterYear = 1930,beforeYear = 2020)
    private LocalDate birthday;


}