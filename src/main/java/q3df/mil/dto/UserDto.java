package q3df.mil.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.dto.dto_for_main_page.TextDto;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String gender;

    private LocalDate birthday;

    private Byte age;

    private String country;

    private String city;

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
