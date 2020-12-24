package q3df.mil.entities.dialog;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import q3df.mil.entities.message.Message;
import q3df.mil.entities.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dialogs",
        indexes = {
                @Index(name = "dialogs_created_idx", columnList = "created DESC")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }


    @Column(name = "delete")
    private Boolean delete;


    /**
     * friendship with message
     */
    @OneToMany(mappedBy = "dialog",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH}, orphanRemoval = true)
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OrderBy(clause = "created DESC")
    private List<Message> messages = new ArrayList<>();

    //add message... simplified
    public void addMessage(Message msg) {
        messages.add(msg);
    }

    /**
     * friendship with user
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})

    @JoinTable(
            name = "user_dialogs",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

}
