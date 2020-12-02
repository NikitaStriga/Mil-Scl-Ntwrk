package q3df.mil.entities.text_entities;


import lombok.*;
import q3df.mil.entities.users_roles.User;

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

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "text_id")
    @NotBlank(message = "Comment  should not be empty !")
    @NotNull(message = "Comment should not be empty !")
    private Text text;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "edited")
    private Boolean edited;

    @Column(name = "deleted")
    private Boolean deleted;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }

    /****************** Relation TextCommentLike + add *********/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "textCommentId",
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
