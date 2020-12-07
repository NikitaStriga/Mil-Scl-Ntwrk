package q3df.mil.dto.photo;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PhotoCommentLikeDto {

    private Long id;

    //photoComment id
    private Long photoCommentId;

    //userId
    private Long userId;

    private String firstName;

    private String lastName;

    private Boolean delete;

}
