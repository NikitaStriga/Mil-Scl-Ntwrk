package q3df.mil.dto.message;

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
public class MessageUpdateDto {

    @NotNull(message = "{messageId.empty}")
    @Positive(message = "{messageId.positive}")
    private Long id;

    @NotNull(message = "{messageText.empty}")
    @NotBlank(message = "{messageText.empty}")
    @NotEmpty(message = "{messageText.empty}")
    @Size(min = 1, max = 350, message = "{messageText.size} {min}-{max} characters!")
    private String text;

}
