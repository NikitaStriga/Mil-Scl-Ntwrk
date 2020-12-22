package q3df.mil.entities.text;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.FetchMode;
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
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "text_comments",
        indexes = {
                @Index(name = "text_comments_created_idx", columnList = "created DESC")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
public class TextComment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "text_id")
    private Text text;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment")
    @NotNull(message = "{textComment.empty}")
    @NotBlank(message = "{textComment.empty}")
    @NotEmpty(message = "{textComment.empty}")
    @Size(min = 1, max = 350, message = "{textComment.size} {min}-{max} characters!")
    private String comment;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "deleted")
    private Boolean deleted;

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdateTime(LocalDateTime.now());
    }

    /****************** Relation TextCommentLike + add *********/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "textComment",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.BatchSize(size = 10)
    /*
        dunno ... but @org.hibernate.annotations.Fetch(FetchMode.SUBSELECT) do double IN operator when hibernate try to download all collections
        if i use @Batch all is ok ... strange behavior...
        Hibernate: select textcommen0_.text_comment_id as text_com3_8_1_,
         textcommen0_.id as id1_8_1_, textcommen0_.id as id1_8_0_,
          textcommen0_.delete as delete2_8_0_,
           textcommen0_.text_comment_id as text_com3_8_0_,
            textcommen0_.user_id as user_id4_8_0_ from text_comment_likes textcommen0_
             where textcommen0_.text_comment_id
              IN (select textcommen0_.id from text_comments textcommen0_ where textcommen0_.text_id
               IN (select texts0_.id from text texts0_ where texts0_.user_id=? ) )
     */
    private Set<TextCommentLike> textCommentsLikeSet = new HashSet<>();

    public void addTextCommentLike(TextCommentLike tcl) {
        textCommentsLikeSet.add(tcl);
    }

    /**********************************************************/


}
