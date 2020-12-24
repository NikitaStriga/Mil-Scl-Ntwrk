package q3df.mil.dto.text.tl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@PropertySource("classpath:messages.properties")
public class TextLikeDto {

    private Long id;

    //Text
    @NotNull(message = "{textId.empty}")
    @Positive(message = "{textId.positive}")
    private Long textId;

    //User
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

    //Username
    private String username;

    //Surname
    private String surname;

}
