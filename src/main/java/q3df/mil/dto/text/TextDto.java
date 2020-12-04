package q3df.mil.dto.text;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class TextDto {

    private Long id;

    //User
    private Long userId;

    private String text;

    private LocalDateTime created;

    private Boolean edited;

    private List<TextCommentDto> textComments=new ArrayList<>();

    private Set<TextLikeDto> textLikes=new HashSet<>();


}
