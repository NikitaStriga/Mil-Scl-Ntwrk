package q3df.mil.dto.text.tcl;

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
public class TextCommentLikeDto {

    private Long id;

    //TextComment
    @NotNull(message = "{textCommentId.empty}")
    @Positive(message = "{textCommentId.positive}")
    private Long textCommentId;

    //User
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

    //Username
    private String username;

    //Surname
    private String surname;


}
