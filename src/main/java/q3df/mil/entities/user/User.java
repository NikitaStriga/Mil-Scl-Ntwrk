package q3df.mil.entities.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import q3df.mil.entities.enums.Gender;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.entities.message.Message;
import q3df.mil.entities.role.Role;
import q3df.mil.entities.text.Text;


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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "user_id_idx",columnList = "id"),
                @Index(name = "user_first_name_idx",columnList = "first_name"),
                @Index(name = "user_last_name_idx",columnList = "last_name"),
                @Index(name = "user_gender_idx",columnList = "gender"),
                @Index(name = "user_birth_date_idx",columnList = "birthday"),
                @Index(name = "user_country_idx",columnList = "country"),
                @Index(name = "user_city_idx",columnList = "city")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "email",unique = true)
    @NotBlank(message = "Mail should not be empty !")
    @NotNull(message = "Mail should not be empty !")
    @Size(max = 150,message = "Mail is to long ! It must be less than 50 characters")
    private String email;

    @Column(name="login",unique = true)
    @NotBlank(message = "Login should not be empty !")
    @NotNull(message = "Login should not be empty !")
    @Size(max = 50,message = "Login is to long ! It must be less than 50 characters")
    private String login;

    @Column(name="password")
    @NotBlank(message = "Password should not be empty !")
    @NotNull(message = "Password should not be empty !")
    @Size(max = 80,message = "Password is to long ! It must be less than 80 characters")
    private String password;

    @Column(name="first_name")
    @NotBlank(message = "First name should not be empty !")
    @NotNull(message = "First name should not be empty !")
    @Size(max = 50,message = "First name is to long ! It must be less than 50 characters")
    private String firstName;

    @Column(name="last_name")
    @NotBlank(message = "Last name should not be empty !")
    @NotNull(message = "Last name should not be empty !")
    @Size(max = 50,message = "Last name is to long ! It must be less than 50 characters")
    private String lastName;

    @Column(name="gender")
//    @Pattern(regexp = "MALE|FEMALE",message = "Please choose correct gender: MAIL or FEMALE")
//    @NotBlank(message = "Please indicate your gender")
    @NotNull(message = "Please indicate your gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "birthday")
    @Past(message = "Invalid date! It's must be in past!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "country")
//    @NotNull(message = "Country should not be empty! Please, enter correct value!")
//    @NotBlank(message = "Country should not be empty! Please, enter correct value!")
    @Size(max = 50,message = "Country is to long ! It must be less than 50 characters")
    private String country;

    @Column(name = "city")
//    @NotNull(message = "City should not be empty! Please, enter correct value!")
//    @NotBlank(message = "City should not be empty! Please, enter correct value!")
    @Size(max = 50,message = "City is to long ! It must be less than 50 characters")
    private String city;

    @Column(name = "registration_time",updatable = false)
    private LocalDateTime registrationTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "delete")
    private Boolean delete;

    @PrePersist
    void onCreate(){
        this.setRegistrationTime(LocalDateTime.now());
        this.setUpdateTime(LocalDateTime.now());

    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
    }


    /***************** Relation to roles  + add ***********/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},orphanRemoval = true)
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
    private List<Text> texts=new ArrayList<>();

    //add new text for user
    public void addText(Text text){
        texts.add(text);
    }

    /********************************************************/




    /***************** Relation to friends  + add ***********/
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

    /*********************************************************/




    /************** Relation to subscribers  + add ***********/
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

    /********************************************************/



    /************** Relation to dialogs  + add ***********/
    @ManyToMany(mappedBy = "users",cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.REMOVE})

    private List<Dialog> dialogs=new ArrayList<>();

    public void addDialog(Dialog dlg){
        dialogs.add(dlg);
    }

    /********************************************************/



    /************** Relation to messages fro outbox  + add ***********/
    @OneToMany(mappedBy = "fromWho",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    private List<Message> outboxMessages=new ArrayList<>();

    public void addOutboxMessage(Message message){
        outboxMessages.add(message);
    }

    /********************************************************/


    /************** Relation to dialogs  + add ***********/
    @OneToMany(mappedBy = "toWho",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    private List<Message> inboxMessages=new ArrayList<>();

    public void addInboxMessage(Message message){
        inboxMessages.add(message);
    }

    /********************************************************/



    /************** Relation to Photos  + add ***********/
//    @OneToMany(mappedBy = "user",
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.PERSIST,
//                    CascadeType.REFRESH},orphanRemoval = true)
//
//    private List<Photo> photos=new ArrayList<>();
//
//    public void addPhoto(Photo p){
//        photos.add(p);
//    }


    /********************************************************/


    /** ManyToMany  friends*/
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = User.class)
    @JoinTable(name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends = new ArrayList<>();

    /** ManyToMany  subscribers*/
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = User.class)
    @JoinTable(name = "user_subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private List<User> subscribers = new ArrayList<>();





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
}