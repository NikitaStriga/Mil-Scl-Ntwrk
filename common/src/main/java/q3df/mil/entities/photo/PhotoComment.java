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
import javax.persistence.FetchType;
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
@Table(name = "photo_comments",
        indexes = {
                @Index(name = "photo_comments_created_idx", columnList = "created DESC")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
public class PhotoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comments")
    @NotNull(message = "{photoComment.empty}")
    @NotBlank(message = "{photoComment.empty}")
    @NotEmpty(message = "{photoComment.empty}")
    @Size(min = 1, max = 350, message = "{photoComment.size} {min}-{max} characters!")
    private String comment;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "delete")
    private Boolean delete;

    @PrePersist
    void onCreate() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdateTime(LocalDateTime.now());
    }


    /**
     * relationship with photoCommentLike
     */

    @OneToMany(mappedBy = "user",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.BatchSize(size = 10)
    private List<PhotoCommentLike> photoCommentLikeList = new ArrayList<>();

    //add photoCommentLike...simplified
    public void addPhotoCommentLike(PhotoCommentLike pcl) {
        photoCommentLikeList.add(pcl);
    }

}
