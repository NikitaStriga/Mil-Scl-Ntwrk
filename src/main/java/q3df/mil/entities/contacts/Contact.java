package q3df.mil.entities.contacts;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@Data
@NoArgsConstructor
public class Contact {

    @Column(name = "address")
    @Size(max = 50,message = "Address is to long ! It must be less than 50 characters")
    private String address;

    @Column(name = "contact_email")
    @Size(max = 50,message = "Mail is to long ! It must be less than 50 characters")
    private String contactMail;

    @Column(name = "contact_number")
    @Size(max = 50,message = "Number is to long ! It must be less than 50 characters")
    private String contactNumber;

    @Column(name = "contact_telegram")
    @Size(max = 50,message = "elegram is to long ! It must be less than 50 characters")
    private String contactTelegram;

    @Column(name = "contact_instagram")
    @Size(max = 50,message = "Instagram is to long ! It must be less than 50 characters")
    private String contactInstagram;

    @Column(name = "contact_facebook")
    @Size(max = 50,message = "Facebook is to long ! It must be less than 50 characters")
    private String contactFacebook;

    @Column(name = "contact_vk")
    @Size(max = 50,message = "VK is to long ! It must be less than 50 characters")
    private String contactVK;


}
