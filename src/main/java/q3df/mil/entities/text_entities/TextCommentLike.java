package q3df.mil.entities.text_entities;


import lombok.*;
import q3df.mil.entities.users_roles.User;

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
    private TextComment textCommentId;

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
