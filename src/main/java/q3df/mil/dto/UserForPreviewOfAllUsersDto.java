package q3df.mil.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.dto.friends_subs_dto.FriendDto;
import q3df.mil.dto.friends_subs_dto.SubscriberDto;
import q3df.mil.entities.users_roles.User;

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

    private LocalDateTime registrationTime;

    private LocalDateTime updateTime;

//    private List<FriendDto> friends;
//    private List<SubscriberDto> subscribers;

}
