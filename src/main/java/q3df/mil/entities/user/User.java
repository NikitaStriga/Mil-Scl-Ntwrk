package q3df.mil.entities.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.entities.message.Message;
import q3df.mil.entities.photo.Photo;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.entities.role.Role;
import q3df.mil.entities.text.Text;
import q3df.mil.entities.text.TextComment;
import q3df.mil.entities.text.TextCommentLike;
import q3df.mil.entities.text.TextLike;
import q3df.mil.validators.ValidDate;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "user_first_name_idx",columnList = "first_name"),
                @Index(name = "user_last_name_idx",columnList = "last_name"),
                @Index(name = "user_gender_idx",columnList = "gender"),
                @Index(name = "user_birth_date_idx",columnList = "birthday"),
                @Index(name = "user_country_idx",columnList = "country"),
                @Index(name = "user_city_idx",columnList = "city"),
                //composite indexes
                @Index(name = "user_city_country_idx",columnList = "city,country"),
                @Index(name = "user_city_country_gender_idx",columnList = "gender, city, country"),
                @Index(name = "user_city_country_gender_birthday_idx",columnList = "gender, city, country, birthday"),
                @Index(name = "user_fname_lname_idx",columnList = "first_name, last_name")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "email")
    @Size(min=3,max = 50,message = "{email.size} {min}-{max} characters!")
    @NotBlank(message = "{email.empty}")
    @NotNull(message = "{email.empty}")
    @NotEmpty(message = "{email.empty}")
    @Email(message = "{email.pattern}")
    private String email;

    @Column(name = "login")
    @NotBlank(message = "{login.empty}")
    @NotNull(message = "{login.empty}")
    @NotEmpty(message = "{login.empty}")
    @Size(min=33,max = 50,message = "{login.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{login.pattern} {regexp}")
    private String login;

    @Column(name="password")
    @NotBlank(message = "{password.empty}")
    @NotNull(message = "{password.empty}")
    @NotEmpty(message = "{password.empty}")
    private String password;

    @Column(name="first_name")
    @NotBlank(message = "{firstName.empty}")
    @NotNull(message = "{firstName.empty}")
    @NotEmpty(message = "{firstName.empty}")
    @Size(min=3,max = 50,message = "{firstName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$",message = "{firstName.pattern} {regexp}")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "{lastName.empty}")
    @NotNull(message = "{lastName.empty}")
    @NotEmpty(message = "{lastName.empty}")
    @Size(min=3,max = 50,message = "{lastName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$",
            message = "{lastName.pattern} {regexp}")
    private String lastName;

    @Column(name="gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "birthday")
    @ValidDate(afterYear = 1930,beforeYear = 2020)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "country")
    @NotBlank(message = "{country.empty}")
    @NotNull(message = "{country.empty}")
    @NotEmpty(message = "{country.empty}")
    @Size(min=3,max = 50,
            message = "{country.size} {min}-{max} characters!")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "{city.empty}")
    @NotNull(message = "{city.empty}")
    @NotEmpty(message = "{city.empty}")
    @Size(min=3,max = 50,message = "{city.size} {min}-{max} characters!")
    private String city;

    @Column(name = "registration_time",updatable = false)
    private LocalDateTime registrationTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "p_change")
    private LocalDateTime pChange;

    @Column(name = "delete")
    private Boolean delete;

    @Column(name = "recovery_code")
    @Size(max = 36)
    private String recoveryCode;

    @PrePersist
    void onCreate(){
        this.setRegistrationTime(LocalDateTime.now());
        this.setUpdateTime(LocalDateTime.now());
        this.setPChange(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
    }


    /***************** Relation to roles  + add ***********/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Role> roles=new ArrayList<>();

    //add new role for user
    public void addRoles(Role addRole){
        roles.add(addRole);
    }

    /********************************************************/



    /***************** Relation to texts  + add ***********/
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Text> texts=new ArrayList<>();

    //add new text for user
    public void addText(Text text){
        texts.add(text);
    }

    /********************************************************/


    /************** Relation to dialogs  + add ***********/
    @ManyToMany(mappedBy = "users",cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.REMOVE})
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Dialog> dialogs=new ArrayList<>();

    public void addDialog(Dialog dlg){
        dialogs.add(dlg);
    }

    /********************************************************/



    /************** Relation to messages / outbox  + add ***********/
    @OneToMany(mappedBy = "fromWho",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Message> outboxMessages=new ArrayList<>();

    public void addOutboxMessage(Message message){
        outboxMessages.add(message);
    }

    /********************************************************/


    /************** Relation to messages / inbox  + add ***********/
    @OneToMany(mappedBy = "toWho",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Message> inboxMessages=new ArrayList<>();

    public void addInboxMessage(Message message){
        inboxMessages.add(message);
    }

    /********************************************************/



    /************** Relation to Photos  + add ***********/
    @OneToMany(mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Photo> photos=new ArrayList<>();

    public void addPhoto(Photo p){
        photos.add(p);
    }


    /********************************************************/


    /** ManyToMany  friends*/
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = User.class)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"),
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"user_id", "friend_id"})
    })
    private List<User> friends = new ArrayList<>();


    /** ManyToMany  subscribers*/
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = User.class)
    @JoinTable(name = "user_subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"),
    uniqueConstraints = {
            @UniqueConstraint(columnNames = {"user_id", "subscriber_id"})
    })
    private List<User> subscribers = new ArrayList<>();




    /*************************************************************************/
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<TextComment> textComments=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<TextLike> textLikes=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<TextCommentLike> textCommentLikes=new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoComment>  photoComments=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoLike> photoLikes=new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoCommentLike> photoCommentLikes=new ArrayList<>();

    /*************************************************************************/



//
//можно подрубить mail
//modelMapper   or objectMapper





//    /************** Relation to messages  + add ***********/
//    @OneToMany(mappedBy = "fromWho",
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.PERSIST,
//                    CascadeType.REFRESH},orphanRemoval = true)
//    private List<Message> messagesFromWho=new ArrayList<>();
//
//    public void addMessagesFromWho(Message message){
//        messagesFromWho.add(message);
//    }
//
//    /********************************************************/
//    /********************************************************/
//
//    /************** Relation to messages  + add ***********/
//    @OneToMany(mappedBy = "fromWho",
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.PERSIST,
//                    CascadeType.REFRESH},orphanRemoval = true)
//    private List<Message> messagesToWho=new ArrayList<>();
//
//    public void addMessageToWho(Message message){
//        messagesToWho.add(message);
//    }
//
//    /********************************************************/

//    /***************** Relation to friends  + add ***********/
//    @OneToMany(mappedBy = "user",
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.PERSIST,
//                    CascadeType.REFRESH},orphanRemoval = true)
//    private List<Friend> friends=new ArrayList<>();
//
//
//    public void addFriend(Friend friend){
//        friends.add(friend);
//    }

//    /*********************************************************/




//    /************** Relation to subscribers  + add ***********/
//    @OneToMany(mappedBy = "user",
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.PERSIST,
//                    CascadeType.REFRESH},orphanRemoval = true)
//    private List<Subscriber> subscribers=new ArrayList<>();
//
//    public void addSubscriber(Subscriber sub){
//        subscribers.add(sub);
//    }

//    /********************************************************/
}
