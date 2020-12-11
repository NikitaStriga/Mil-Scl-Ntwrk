package q3df.mil.entities.text;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import q3df.mil.entities.user.User;

import javax.persistence.*;


@Entity
@Table(name = "text_likes",
        indexes = {
        @Index(name = "text_like_text_id_idx",columnList = "text_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"text_id", "user_id"})}
        )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "text_id")
    private Text textId;


    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "delete")
    private Boolean delete;



}
