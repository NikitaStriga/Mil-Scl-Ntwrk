package q3df.mil.entities.dialog;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import q3df.mil.entities.message.Message;
import q3df.mil.entities.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dialogs")
@Data
@NoArgsConstructor
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }


    @Column(name = "delete")
    private Boolean delete;


    @OneToMany(mappedBy = "dialog",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    private List<Message> messages=new ArrayList<>();

    public void addMessage(Message msg){
        messages.add(msg);
    }


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})

    @JoinTable(
            indexes = {
                    @Index(name = "dialog_user_id_idx",columnList = "user_id")
            },
            name = "user_dialogs",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;


}
