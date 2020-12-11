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
@Table(name = "text_comment_likes",
        indexes = {
        @Index(name = "text_comment_like_text_comment_id_idx",columnList = "text_comment_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"text_comment_id", "user_id"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextCommentLike {

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
    @JoinColumn(name = "text_comment_id")
    private TextComment textComment;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "delete")
    private Boolean delete;
}
