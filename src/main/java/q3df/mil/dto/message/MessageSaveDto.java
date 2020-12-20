package q3df.mil.dto.message;

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
public class MessageSaveDto {

    @ApiModelProperty(position = 1)
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long fromWhoId;

    @ApiModelProperty(position = 2)
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long toWhoId;

//    @NotNull(message = "{userId.empty}")
//    @Positive(message = "{userId.positive}")
//    private Long dialogId;

    @ApiModelProperty(position = 3)
    @NotNull(message = "{messageText.empty}")
    @NotBlank(message = "{messageText.empty}")
    @NotEmpty(message = "{messageText.empty}")
    @Size(min = 1, max = 350, message = "{messageText.size} {min}-{max} characters!")
    private String text;

}
