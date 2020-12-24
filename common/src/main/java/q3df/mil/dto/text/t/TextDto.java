package q3df.mil.dto.text.t;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.dto.text.tc.TextCommentDto;
import q3df.mil.dto.text.tl.TextLikeDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@PropertySource("classpath:messages.properties")
public class TextDto {

    private Long id;

    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

    private String username;

    private String surname;

    @NotNull(message = "{text.empty}")
    @NotBlank(message = "{text.empty}")
    @NotEmpty(message = "{text.empty}")
    @Size(min = 1, max = 350, message = "{text.size}")
    private String text;

    private LocalDateTime created;

    private LocalDateTime updateTime;

    private List<TextCommentDto> textComments=new ArrayList<>();

    private Set<TextLikeDto> textLikes=new HashSet<>();


}
