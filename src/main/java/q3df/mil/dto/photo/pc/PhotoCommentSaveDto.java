package q3df.mil.dto.photo.pc;

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
public class PhotoCommentSaveDto {

    //photoId
    @NotNull(message = "{photoId.empty}")
    @Positive(message = "{photoId.positive}")
    private Long photoId;

    //userId
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

    @NotNull(message = "{photoComment.empty}")
    @NotBlank(message = "{photoComment.empty}")
    @NotEmpty(message = "{photoComment.empty}")
    @Size(min = 1, max = 350, message = "{photoComment.size} {min}-{max} characters!")
    private String comment;


}
