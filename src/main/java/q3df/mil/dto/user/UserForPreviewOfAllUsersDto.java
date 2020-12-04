package q3df.mil.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.role.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserForPreviewOfAllUsersDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String gender;

    private String country;

    private String city;

    private String address;

    private String contactMail;

    private String contactNumber;

    private String contactTelegram;

    private String contactInstagram;

    private String contactFacebook;

    private String contactVK;

    private LocalDate birthday;

    private LocalDateTime registrationTime;

    private LocalDateTime updateTime;

    private List<Role> roles;


//    private List<FriendDto> friends;
//    private List<SubscriberDto> subscribers;

}
