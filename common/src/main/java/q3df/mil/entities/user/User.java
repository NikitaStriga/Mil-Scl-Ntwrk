package q3df.mil.entities.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.entities.contacts.Contact;
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
import q3df.mil.exception.EntityAddingException;
import q3df.mil.validators.ValidDate;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
        indexes = {
                //all indexes define in sql script
                //cuz some of them with lower function
                //which cannot be written using Java code
                //hibernate does not provide such features
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
@org.hibernate.annotations.BatchSize(size = 10)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    @Size(min = 3, max = 50, message = "{email.size} {min}-{max} characters!")
    @NotBlank(message = "{email.empty}")
    @NotNull(message = "{email.empty}")
    @NotEmpty(message = "{email.empty}")
    @Email(message = "{email.pattern}")
    private String email;

    @Column(name = "login")
    @NotBlank(message = "{login.empty}")
    @NotNull(message = "{login.empty}")
    @NotEmpty(message = "{login.empty}")
    @Size(min = 3, max = 50, message = "{login.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$",
            message = "{login.pattern} {regexp}")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "{password.empty}")
    @NotNull(message = "{password.empty}")
    @NotEmpty(message = "{password.empty}")
    @Size(min = 3, max = 60, message = "{password.size} {min}-{max} characters!")
    @Pattern(regexp = "^[0-9A-z_@!@#$%^&*)(\\-\\]\\[]*$", message = "{password.pattern} {regexp}")
    private String password;

    @Column(name = "first_name")
    @NotBlank(message = "{firstName.empty}")
    @NotNull(message = "{firstName.empty}")
    @NotEmpty(message = "{firstName.empty}")
    @Size(min = 3, max = 50, message = "{firstName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$", message = "{firstName.pattern} {regexp}")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "{lastName.empty}")
    @NotNull(message = "{lastName.empty}")
    @NotEmpty(message = "{lastName.empty}")
    @Size(min = 3, max = 50, message = "{lastName.size} {min}-{max} characters!")
    @Pattern(regexp = "^[A-z]*$",
            message = "{lastName.pattern} {regexp}")
    private String lastName;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "birthday")
    @ValidDate(afterYear = 1930, beforeYear = 2020)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "country")
    @NotBlank(message = "{country.empty}")
    @NotNull(message = "{country.empty}")
    @NotEmpty(message = "{country.empty}")
    @Size(min = 3, max = 50,
            message = "{country.size} {min}-{max} characters!")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "{city.empty}")
    @NotNull(message = "{city.empty}")
    @NotEmpty(message = "{city.empty}")
    @Size(min = 3, max = 50, message = "{city.size} {min}-{max} characters!")
    private String city;

    @Column(name = "registration_time", updatable = false)
    private LocalDateTime registrationTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "p_change")
    private LocalDateTime pChange;

    @Column(name = "delete")
    private Boolean delete;

    @Column(name = "recovery_code", insertable = false, nullable = true)
    @Size(max = 36)
    private String recoveryCode;

    @Embedded
    private Contact contact;

    @PrePersist
    void onCreate() {
        this.setRegistrationTime(LocalDateTime.now());
        this.setUpdateTime(LocalDateTime.now());
        this.setPChange(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdateTime(LocalDateTime.now());
    }


    /**
     * relationship with roles
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private Set<Role> roles = new HashSet<>();

    //add new role
    public void addRoles(Role addRole) {
        if (addRole == null) {
            throw new EntityAddingException("Role");
        }
        if (addRole.getUser() != null) {
            throw new EntityAddingException("Role", "User");
        }
        roles.add(addRole);
        addRole.setUser(this);
    }


    /**
     * relationship with texts
     */
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OrderBy(clause = "created DESC")
    private List<Text> texts = new ArrayList<>();

    //add new text
    public void addText(Text text) {
        if (text == null) {
            throw new EntityAddingException("Text");
        }
        if (text.getUser() != null) {
            throw new EntityAddingException("Text", "User");
        }
        texts.add(text);
        text.setUser(this);
    }


    /**
     * relationship with dialogs
     */
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Dialog> dialogs = new ArrayList<>();

    //add new dialog
    public void addDialog(Dialog dlg) {
        if (dlg == null) {
            throw new EntityAddingException("Text");
        }
        if (dlg.getUsers().contains(this)) {
            throw new EntityAddingException("Dialog", "User");
        }
        dialogs.add(dlg);
        dlg.getUsers().add(this);
    }


    /**
     * relationship with outbox messages
     */
    @OneToMany(mappedBy = "fromWho",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Message> outboxMessages = new ArrayList<>();

    //add outbox message
    public void addOutboxMessage(Message message) {
        if (message == null) {
            throw new EntityAddingException("Message");
        }
        if (message.getFromWho() != null) {
            throw new EntityAddingException("Mesage", "User");
        }
        outboxMessages.add(message);
        message.setFromWho(this);
    }


    /**
     * relationship with inbox messages
     */
    @OneToMany(mappedBy = "toWho",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<Message> inboxMessages = new ArrayList<>();

    //add inbox message
    public void addInboxMessage(Message message) {
        if (message == null) {
            throw new EntityAddingException("Message");
        }
        if (message.getToWho() != null) {
            throw new EntityAddingException("Mesage", "User");
        }
        outboxMessages.add(message);
        message.setToWho(this);
    }


    /**
     * relationship with photos
     */
    @OneToMany(mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OrderBy(clause = "created DESC")
    private List<Photo> photos = new ArrayList<>();

    //add photos
    public void addPhoto(Photo photo) {
        if (photo == null) {
            throw new EntityAddingException("Text");
        }
        if (photo.getUser() != null) {
            throw new EntityAddingException("Text", "User");
        }
        photos.add(photo);
        photo.setUser(this);
    }


    /**
     * ManyToMany  friends
     */
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


    /**
     * ManyToMany  subscribers
     */
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


    /**
     * the relations presented below are needed only for the cascade DELETE to work at the database level,
     * I did not add addEntity() to them
     */
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)
    private List<TextComment> textComments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<TextLike> textLikes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<TextCommentLike> textCommentLikes = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoComment> photoComments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoLike> photoLikes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoCommentLike> photoCommentLikes = new ArrayList<>();


}
