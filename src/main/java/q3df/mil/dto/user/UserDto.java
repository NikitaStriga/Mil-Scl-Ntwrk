package q3df.mil.dto.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.dto.text.TextDto;
import q3df.mil.validators.ValidDate;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Id  should not be empty !")
    @NotNull(message = "Id  should not be empty !")
    @NotEmpty(message = "Id should not be empty !")
    @Size(message = "Id should be positive!")
    private Long id;

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

    private String address;

    private String contactMail;

    private String contactNumber;

    private String contactTelegram;

    private String contactInstagram;

    private String contactFacebook;

    private String contactVK;



    //lists
//    private List<Role> roles=new ArrayList<>(4);

    private List<TextDto> texts=new ArrayList<>();

//    private List<Friend> friends=new ArrayList<>();

//    private List<Subscriber> subscribers=new ArrayList<>();

//    private List<Dialog> dialogs=new ArrayList<>();

//    private List<Photo> photos=new ArrayList<>();




}
