package q3df.mil.dto.photo.pc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.photo.PhotoCommentLike;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PhotoCommentDto {

    private Long id;

    //photoId
    private Long photoId;

    //userId
    private Long userId;

    private String firstName;

    private String lastName;

    private String comments;

    private LocalDateTime created;

    private LocalDateTime updateTime;

    private Boolean delete;

    private List<PhotoCommentLike> photoCommentLikeList=new ArrayList<>();


}
