package q3df.mil.dto.photo.pcl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@PropertySource("classpath:messages.properties")
public class PhotoCommentLikeSaveDto {


    //photoComment id
    @NotNull(message = "{photoCommentId.empty}")
    @Positive(message = "{photoCommentId.positive}")
    private Long photoCommentId;

    //userId
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

}
