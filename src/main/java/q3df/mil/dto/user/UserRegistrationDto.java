package q3df.mil.dto.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import q3df.mil.validators.ValidDate;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class UserRegistrationDto {


    @NotBlank(message = "Mail should not be empty !")
    @NotNull(message = "Mail should not be empty !")
    @NotEmpty(message = "Mail should not be empty !")
    @Size(max = 50,message = "Email must be less than 50 characters")
    @Pattern(regexp = "^[\\S]*$")
    private String email;


    @NotBlank(message = "Login should not be empty !")
    @NotNull(message = "Login should not be empty !")
    @NotEmpty(message = "Login should not be empty !")
    @Size(min=5, max = 30, message = "Login must be between 5 and 30 characters")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "Wrong login. Allowed characters 0-9, A-z, _ ! @ # $ % ^ & * ( ) - and NO whitespaces at start and at the end!")
    private String login;

    @NotBlank(message = "Password should not be empty !")
    @NotNull(message = "Password should not be empty !")
    @NotEmpty(message = "Password should not be empty !")
    @Size(min=5, max = 30, message = "Password must be between 5 and 30 characters")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "Wrong password. Allowed characters 0-9, A-z, _ ! @ # $ % ^ & * ( ) - and NO whitespaces at start and at the end! ")
    private String password;

    @NotBlank(message = "First name should not be empty !")
    @NotNull(message = "First name should not be empty !")
    @NotEmpty(message = "First name should not be empty !")
    @Size(max = 50,message = "First name is to long ! It must be less than or equal to 50 characters!")
    @Pattern(regexp = "^[A-z]*$",message = "Please enter correct name!")
    private String firstName;

    @NotBlank(message = "Last name should not be empty !")
    @NotNull(message = "Last name should not be empty !")
    @NotEmpty(message = "Last name should not be empty !")
    @Size(max = 50,message = "Last name is to long ! It must be less than or equal to 50 characters!")
    @Pattern(regexp = "^[A-z]*$",message = "Please enter correct surname!")
    private String lastName;

    @NotBlank(message = "Gender  should not be empty !")
    @NotNull(message = "Gender  should not be empty !")
    @NotEmpty(message = "Gender  should not be empty !")
    @Pattern(regexp = "(?i)male|female",message = "Please enter correct gender (male or female) !")
    private String gender;

    @NotBlank(message = "Country  should not be empty !")
    @NotNull(message = "Country  should not be empty !")
    @NotEmpty(message = "Country  should not be empty !")
    @Size(max = 30, message = "To long country name !")
    private String country;

    @NotBlank(message = "City  should not be empty !")
    @NotNull(message = "City  should not be empty !")
    @NotEmpty(message = "City  should not be empty !")
    @Size(max = 30, message = "To long city name !")
    private String city;


    @ValidDate
    private LocalDate birthday;


}