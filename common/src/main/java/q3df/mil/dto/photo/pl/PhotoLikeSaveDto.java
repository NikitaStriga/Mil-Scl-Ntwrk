package q3df.mil.dto.photo.pl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class PhotoLikeSaveDto {

    //photoId
    @NotNull(message = "{photoId.empty}")
    @Positive(message = "{photoId.positive}")
    private Long photoId;

    //userId
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

}
