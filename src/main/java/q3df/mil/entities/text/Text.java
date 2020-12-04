package q3df.mil.entities.text;


import lombok.*;
import q3df.mil.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "text",
        indexes = {
            @Index(name = "text_user_id_idx",columnList = "user_id")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH })
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    @NotBlank(message = "Text  should not be empty !")
    @NotNull(message = "Text  should not be empty !")
    private String text;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "edited")
    private Boolean edited;

    @Column(name= "delete")
    private Boolean delete;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }


    /************** Relation to TextComment  + add ***********/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "text",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    private List<TextComment> textComments=new ArrayList<>();

    //add comments
    public void addComments(TextComment comment){
        textComments.add(comment);
    }

    /**********************************************************/




    /************** Relation to TextLike  + add ***********/
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "textId",
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},orphanRemoval = true)
    private Set<TextLike> textLikes=new HashSet<>();


    //add comments
    public void addLikes(TextLike likes){
        textLikes.add(likes);
    }

    /**********************************************************/

}
