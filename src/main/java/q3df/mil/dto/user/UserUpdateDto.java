package q3df.mil.dto.user;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.validators.ValidDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class UserUpdateDto {

    @ApiModelProperty(position = 1)
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long id;

    @ApiModelProperty(position = 2)
    @NotBlank(message = "{firstName.empty}")
    @NotNull(message = "{firstName.empty}")
    @NotEmpty(message = "{firstName.empty}")
    @Size(min = 3, max = 50, message = "{firstName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$", message = "{firstName.pattern} {regexp}")
    private String firstName;

    @ApiModelProperty(position = 3)
    @NotBlank(message = "{lastName.empty}")
    @NotNull(message = "{lastName.empty}")
    @NotEmpty(message = "{lastName.empty}")
    @Size(min = 3, max = 50, message = "{lastName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$",
            message = "{lastName.pattern} {regexp}")
    private String lastName;

    @ApiModelProperty(position = 4)
    @NotBlank(message = "{gender.empty}")
    @NotNull(message = "{gender.empty}")
    @NotEmpty(message = "{gender.empty}")
    @Pattern(regexp = "(?i)male|female",
            message = "{gender.pattern} {regexp}")
    private String gender;

    @ApiModelProperty(position = 5)
    @NotBlank(message = "{country.empty}")
    @NotNull(message = "{country.empty}")
    @NotEmpty(message = "{country.empty}")
    @Size(min = 3, max = 50,
            message = "{country.size} {min}-{max} characters!")
    private String country;

    @ApiModelProperty(position = 6)
    @NotBlank(message = "{city.empty}")
    @NotNull(message = "{city.empty}")
    @NotEmpty(message = "{city.empty}")
    @Size(min = 3, max = 50, message = "{city.size} {min}-{max} characters!")
    private String city;

    @ApiModelProperty(position = 7)
    @ValidDate(afterYear = 1930, beforeYear = 2020)
    private LocalDate birthday;


}