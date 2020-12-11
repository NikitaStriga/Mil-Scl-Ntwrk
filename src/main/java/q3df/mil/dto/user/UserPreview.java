package q3df.mil.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPreview {

    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    private String country;

    private String city;

    private LocalDate birthday;

    private LocalDateTime registrationTime;

    private LocalDateTime updateTime;

}
