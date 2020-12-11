package q3df.mil.entities.photo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photo_comments",
        indexes = {
            @Index(name = "photo_comment_photo_id_idx",columnList = "photo_id")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoComment {


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

    @Column(name = "comments")
    private String comment;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "delete")
    private Boolean delete;

    @PrePersist
    void onCreate(){
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
    }



    /****** Relation with PhotoCommentLikes + add ****************/
    @OneToMany(mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<PhotoCommentLike> photoCommentLikeList=new ArrayList<>();

    public void addPhotoCommentLike(PhotoCommentLike pcl){
        photoCommentLikeList.add(pcl);
    }

    /****************************************************************/


}
