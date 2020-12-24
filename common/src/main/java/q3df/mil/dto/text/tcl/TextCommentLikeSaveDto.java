package q3df.mil.dto.text.tcl;


import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(position = 1)
    @NotNull(message = "{textCommentId.empty}")
    @Positive(message = "{textCommentId.positive}")
    private Long textCommentId;

    //User
    @ApiModelProperty(position = 2)
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;


}
