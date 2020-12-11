package q3df.mil.dto.text.tl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class TextLikeSaveDto {


    @NotNull(message = "{textId.empty}")
    @Positive(message = "{textId.positive}")
    //Text
    private Long textId;

    //User
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;


}
