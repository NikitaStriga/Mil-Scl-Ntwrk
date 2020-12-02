package q3df.mil.entities.photos_entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.users_roles.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photos",
        indexes = {
            @Index(name = "photo_user_id_idx",columnList = "user_id")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "description")
    private String description;

    @Column(name = "main_photo")
    private Boolean mainPhoto;

    @Column(name = "path")
    private String path;

    @Column(name = "delete")
    private Boolean delete;



    /**********Relation with PhotoComment + add *************/
    @OneToMany(mappedBy = "photo",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<PhotoComment> photoComments=new ArrayList<>();

    public void addPhotoComments(PhotoComment pc){
        photoComments.add(pc);
    }

    /********************************************************/


    /**********Relation with PhotoLikes + add *************/
    @OneToMany(mappedBy = "photo",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private List<PhotoLike> photoLikes=new ArrayList<>();

    public void addPhotoLike(PhotoLike pl){
        photoLikes.add(pl);
    }
    /********************************************************/


    @PrePersist
    void onCreate(){
        this.created=LocalDateTime.now();
    }
}
