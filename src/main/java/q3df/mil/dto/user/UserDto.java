package q3df.mil.dto.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.dto.text.t.TextDto;
import q3df.mil.entities.photo.Photo;
import q3df.mil.validators.ValidDate;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class UserDto {

    @NotBlank(message = "{userId.empty}")
    @NotNull(message = "{userId.empty}")
    @NotEmpty(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long id;

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


    private List<TextDto> texts=new ArrayList<>();


  private List<Photo> photos=new ArrayList<>();




}
