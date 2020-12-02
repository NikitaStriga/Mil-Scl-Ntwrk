package q3df.mil.dto.dto_for_main_page;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TextCommentLikeDto {

    private Long id;

    //TextComment
    private Long textCommentId;

    //User
    private Long userId;

    //Username
    private String username;

    //Surname
    private String surname;

}
