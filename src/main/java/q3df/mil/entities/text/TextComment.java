package q3df.mil.entities.text;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import q3df.mil.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "text_comments",
            indexes = {
        @Index(name = "text_comment_text_id_idx",columnList = "text_id")
            })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextComment {


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
    private Text text;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "deleted")
    private Boolean deleted;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
    }

    /****************** Relation TextCommentLike + add *********/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "textComment",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    private Set<TextCommentLike> textCommentsLikeSet=new HashSet<>();

    public void addTextCommentLike(TextCommentLike tcl){
        textCommentsLikeSet.add(tcl);
    }

    /**********************************************************/


}
