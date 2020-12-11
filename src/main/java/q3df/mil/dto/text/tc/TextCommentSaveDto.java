package q3df.mil.dto.text.tc;

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
public class TextCommentSaveDto {


    //Text
    @NotNull(message = "{textId.empty}")
    @Positive(message = "{textId.positive}")
    private Long textId;

    //User
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

    @NotNull(message = "{textComment.empty}")
    @NotBlank(message = "{textComment.empty}")
    @NotEmpty(message = "{textComment.empty}")
    @Size(min = 1, max = 350, message = "{textComment.size} {min}-{max} characters!")
    private String comment;


}
