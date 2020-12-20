package q3df.mil.dto.photo.pc;


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
public class PhotoCommentUpdateDto {

    @ApiModelProperty(position = 1)
    @NotNull(message = "{photoCommentId.empty}")
    @Positive(message = "{photoCommentId.positive}")
    private Long id;

    @ApiModelProperty(position = 2)
    @NotNull(message = "{photoComment.empty}")
    @NotBlank(message = "{photoComment.empty}")
    @NotEmpty(message = "{photoComment.empty}")
    @Size(min = 1, max = 350, message = "{photoComment.size} {min}-{max} characters!")
    private String comment;
}
