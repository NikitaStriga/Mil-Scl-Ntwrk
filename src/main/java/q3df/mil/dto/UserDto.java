package q3df.mil.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.dto.dto_for_main_page.TextDto;
import q3df.mil.validators.ValidDate;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class UserDto {

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

    @Past(message = "Birthday must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Byte age;

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
