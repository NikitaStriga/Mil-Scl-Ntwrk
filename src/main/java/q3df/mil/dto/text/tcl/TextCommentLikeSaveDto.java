package q3df.mil.dto.text.tcl;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class TextCommentLikeSaveDto {


    //TextComment
    @NotNull(message = "{textCommentId.empty}")
    @Positive(message = "{textCommentId.positive}")
    private Long textCommentId;

    //User
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;


}
