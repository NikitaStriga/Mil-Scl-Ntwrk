package q3df.mil.dto.photo.pcl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
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
