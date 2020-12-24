package q3df.mil.dto.text.t;

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
public class TextUpdateDto {

    @ApiModelProperty(position = 1)
    @NotNull(message = "{textId.empty}")
    @Positive(message = "{textId.positive}")
    private Long id;

    @ApiModelProperty(position = 2)
    @NotNull(message = "{text.empty}")
    @NotBlank(message = "{text.empty}")
    @NotEmpty(message = "{text.empty}")
    @Size(min = 1, max = 350, message = "{text.size} {min}-{max} characters!")
    private String text;


}
