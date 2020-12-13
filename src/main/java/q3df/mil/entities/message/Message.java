package q3df.mil.entities.message;

import lombok.*;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.entities.dialog.Dialog;
import q3df.mil.entities.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages",
        indexes = {
        @Index(name = "created_idx",columnList = "created DESC")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
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
            CascadeType.REFRESH, })
    @JoinColumn(name = "to_who")
    private User toWho;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "update_time")
    private LocalDateTime updateTime;


    @Column(name = "text")
    @NotNull(message = "{messageText.empty}")
    @NotBlank(message = "{messageText.empty}")
    @NotEmpty(message = "{messageText.empty}")
    @Size(min = 1, max = 350, message = "{messageText.size} {min}-{max} characters!")
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

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
    }

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }


}
