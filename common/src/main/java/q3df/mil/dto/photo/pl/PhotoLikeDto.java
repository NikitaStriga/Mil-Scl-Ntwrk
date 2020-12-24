package q3df.mil.dto.photo.pl;


import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
public class PhotoLikeDto {

    private Long id;

    //photoId
    private Long photoId;

    //userId
    private Long userId;

    private String firstName;

    private String lastName;

    private Boolean delete;
}
