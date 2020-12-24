package q3df.mil.dto.text.tc;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class TextCommentUpdateDto {

    @ApiModelProperty(position = 1)
    @NotNull(message = "{textComment.empty}")
    @Positive(message = "{textComment.positive}")
    private Long id;

    @ApiModelProperty(position = 2)
    @NotNull(message = "{textComment.empty}")
    @NotBlank(message = "{textComment.empty}")
    @NotEmpty(message = "{textComment.empty}")
    @Size(min = 1, max = 350, message = "{textComment.size} {min}-{max} characters!")
    private String comment;

}
