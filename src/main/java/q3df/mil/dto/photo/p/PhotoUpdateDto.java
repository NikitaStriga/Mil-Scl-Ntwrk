package q3df.mil.dto.photo.p;


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
public class PhotoUpdateDto {

    @ApiModelProperty(position = 1)
    @NotNull(message = "{photoId.empty}")
    @Positive(message = "{photoId.positive}")
    private Long id;

    @ApiModelProperty(position = 2)
    @NotNull(message = "{description.empty}")
    @NotBlank(message = "{description.empty}")
    @NotEmpty(message = "{description.empty}")
    @Size(min = 1, max = 350, message = "{description.size} {min}-{max} characters!")
    private String description;

    @ApiModelProperty(position = 3)
    private Boolean mainPhoto;

}
