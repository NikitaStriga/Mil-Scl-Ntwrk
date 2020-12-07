package q3df.mil.entities.photo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.user.User;

import javax.persistence.*;

@Entity
@Table(name = "photo_likes",
        indexes = {
            @Index(name = "photo_like_photo_id_idx1",columnList = "photo_id")
        },
        uniqueConstraints = {@UniqueConstraint(columnNames = {"photo_id", "user_id"})})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoLike {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "photo_id")
    private Photo photo;


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