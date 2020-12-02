package q3df.mil.entities.photos_entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.users_roles.User;

import javax.persistence.*;

@Entity
@Table(name = "photo_comment_likes",
        indexes = {
            @Index(name = "photo_comment_like_photo_comment_id_idx",columnList = "photo_comment_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"photo_comment_id", "user_id"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "photo_comment_id")
    private PhotoComment photoComment;


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
