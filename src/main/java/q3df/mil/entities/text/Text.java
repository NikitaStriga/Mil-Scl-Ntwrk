package q3df.mil.entities.text;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "text",
        indexes = {
            @Index(name = "created_idx",columnList = "created DESC")
        })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:ValidationMessages.properties")
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
    @NotNull(message = "{text.empty}")
    @NotBlank(message = "{text.empty}")
    @NotEmpty(message = "{text.empty}")
    @Size(min = 1, max = 350, message = "{text.size}")
    private String text;

    @Column(name = "created",updatable = false)
    private LocalDateTime created;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name= "delete")
    private Boolean delete;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdateTime(LocalDateTime.now());
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
