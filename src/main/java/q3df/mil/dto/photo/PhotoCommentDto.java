package q3df.mil.dto.photo;

import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.photo.Photo;
import q3df.mil.entities.photo.PhotoCommentLike;
import q3df.mil.entities.user.User;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
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

    private Boolean edited;

    private Boolean delete;

    private List<PhotoCommentLike> photoCommentLikeList=new ArrayList<>();


}
