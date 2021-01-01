package q3df.mil.dto.photo.p;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@AllArgsConstructor
@PropertySource("classpath:messages.properties")
@Builder
public class PhotoSaveDto {

    //userId
    @ApiModelProperty(position = 1)
    @NotNull(message = "{userId.empty}")
    @Positive(message = "{userId.positive}")
    private Long userId;

    @ApiModelProperty(position = 2)
    @NotNull(message = "{description.empty}")
    @NotBlank(message = "{description.empty}")
    @NotEmpty(message = "{description.empty}")
    @Size(min = 1, max = 350, message = "{text.size} {min}-{max} characters!")
    private String description;

    //path will generate in amazonSender
//    @ApiModelProperty(position = 3)
//    @NotNull(message = "{path.empty}")
//    @NotBlank(message = "{path.empty}")
//    @NotEmpty(message = "{path.empty}")
//    @Size(min = 1, max = 100, message = "{path.size} {min}-{max} characters!")
//    private String path;

}
