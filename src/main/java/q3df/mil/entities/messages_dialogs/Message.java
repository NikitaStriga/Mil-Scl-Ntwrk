package q3df.mil.entities.messages_dialogs;

import lombok.*;
import q3df.mil.entities.users_roles.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages",
        indexes = {
        @Index(name = "message_dialog_id_idx",columnList = "dialog_id")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "from_who")
    private User fromWho;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "to_who")
    private User toWho;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "edited")
    private Boolean edited;

    @Column(name = "text")
    private String text;

    @Column(name = "delete")
    private Boolean delete;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;


    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }


}
