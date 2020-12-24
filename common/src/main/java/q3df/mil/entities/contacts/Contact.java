package q3df.mil.entities.contacts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Embeddable
@Data
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
public class Contact {

    @Column(name = "address", insertable = false, columnDefinition = "varchar(50) default ''")
    @Size(max = 50, message = "{address.size} {min}-{max} characters!")
    private String address;

    @Column(name = "contact_email", insertable = false, columnDefinition = "varchar(50) default ''")
    @Size(max = 50, message = "{contactMail.size} {min}-{max} characters!")
    @Email(message = "{contactMail.pattern}")
    private String contactMail;

    @Column(name = "contact_telegram", insertable = false, columnDefinition = "varchar(50) default ''")
    @Size(max = 50, message = "{contactTelegram.size} {min}-{max} characters!")
    @Pattern(regexp = "t.me/@.*", message = "{contactTelegram.pattern}")
    private String contactTelegram;

    @Column(name = "contact_instagram", insertable = false, columnDefinition = "varchar(50) default ''")
    @Size(max = 50, message = "{contactInstagram.size} {min}-{max} characters!")
    @Pattern(regexp = "www\\.instagram\\.com/.*", message = "{contactInstagram.pattern}")
    private String contactInstagram;

    @Column(name = "contact_facebook", insertable = false, columnDefinition = "varchar(50) default ''")
    @Size(max = 50, message = "{contactFacebook.size} {min}-{max} characters!")
    @Pattern(regexp = "www\\.facebook\\.com/.*", message = "{contactFacebook.pattern}")
    private String contactFacebook;

    @Column(name = "contact_vk", insertable = false, columnDefinition = "varchar(50) default ''")
    @Size(max = 50, message = "{contactVK.size} {min}-{max} characters!")
    @Pattern(regexp = "www\\.vk\\.com/.*", message = "{contactVK.pattern}")
    private String contactVK;

}
