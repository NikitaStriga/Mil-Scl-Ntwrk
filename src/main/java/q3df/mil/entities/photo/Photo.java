package q3df.mil.entities.photo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.springframework.context.annotation.PropertySource;
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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photos",
        indexes = {
            @Index(name = "photos_created_idx",columnList = "created DESC")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
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

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "description")
    @NotNull(message = "{description.empty}")
    @NotBlank(message = "{description.empty}")
    @NotEmpty(message = "{description.empty}")
    @Size(min = 1, max = 350, message = "{text.size} {min}-{max} characters!")
    private String description;

    @Column(name = "main_photo")
    private Boolean mainPhoto;

    @Column(name = "path")
    @NotNull(message = "{path.empty}")
    @NotBlank(message = "{path.empty}")
    @NotEmpty(message = "{path.empty}")
    @Size(min = 1, max = 100, message = "{path.size} {min}-{max} characters!")
    private String path;

    @Column(name = "delete")
    private Boolean delete;

    @PrePersist
    void onCreate(){
        this.created=LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
    }



    /**********Relation with PhotoComment + add *************/
    @OneToMany(mappedBy = "photo",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OrderBy(clause = "created DESC")
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
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    private List<PhotoLike> photoLikes=new ArrayList<>();

    public void addPhotoLike(PhotoLike pl){
        photoLikes.add(pl);
    }
    /********************************************************/


}
