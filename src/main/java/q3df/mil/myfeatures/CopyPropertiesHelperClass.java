package q3df.mil.myfeatures;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import q3df.mil.dto.user.UserUpdateDto;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.user.User;

import java.time.LocalDate;

@Component
@NoArgsConstructor
public class CopyPropertiesHelperClass {


    /**
     *
     * copy properties from userDto to User, used by update in {@link q3df.mil.service.impl.UserServiceImpl }
     * @param fromWho userDto  witch contains new data
     * @param toWho object of user where we set data
     */
    public  void  copyUserProperties(UserUpdateDto fromWho, User toWho){
        String firstName = fromWho.getFirstName();
        if(firstName!=null) toWho.setFirstName(firstName);
        String lastName = fromWho.getLastName();
        if(lastName!=null) toWho.setLastName(lastName);
        try{
            Gender gender = Gender.valueOf(fromWho.getGender());
            toWho.setGender(gender);
        }catch (IllegalArgumentException | NullPointerException ex){

        }
        LocalDate birthday = fromWho.getBirthday();
        if (birthday!=null) toWho.setBirthday(birthday);
        String city = fromWho.getCity();
        if (city!=null) toWho.setCity(city);
        String country = fromWho.getCountry();
        if (country!=null) toWho.setCountry(country);
    }



}
