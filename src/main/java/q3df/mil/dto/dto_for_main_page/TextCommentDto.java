package q3df.mil.dto.dto_for_main_page;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
public class TextCommentDto {

    private Long id;

    //Text
    private Long textId;

    //User
    private Long userId;

    //UserName
    private String username;

    //Surname
    private String surname;

    private String comment;

    private LocalDateTime created;

    private Boolean edited;

    private Set<TextCommentLikeDto> textCommentsLikeSet=new HashSet<>();



}
