package q3df.mil.dto.text.tc;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import q3df.mil.dto.text.tcl.TextCommentLikeDto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
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

    private LocalDateTime updateTime;

    private Set<TextCommentLikeDto> textCommentsLikeSet=new HashSet<>();



}
