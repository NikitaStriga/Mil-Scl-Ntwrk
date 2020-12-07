package q3df.mil.dto.photo;

import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.entities.photo.PhotoComment;
import q3df.mil.entities.photo.PhotoLike;
import q3df.mil.entities.user.User;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
public class PhotoDto {

    private Long id;
    //userId
    private Long userId;

    private LocalDateTime created;

    private String description;

    private Boolean mainPhoto;

    private String path;

    private Boolean delete;

    private List<PhotoCommentDto> photoComments=new ArrayList<>();

    private List<PhotoLikeDto> photoLikes=new ArrayList<>();


}
