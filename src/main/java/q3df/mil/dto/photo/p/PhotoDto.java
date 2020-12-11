package q3df.mil.dto.photo.p;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import q3df.mil.dto.photo.pc.PhotoCommentDto;
import q3df.mil.dto.photo.pl.PhotoLikeDto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PhotoDto {

    private Long id;

    //userId
    private Long userId;

    private LocalDateTime created;

    private LocalDateTime updateTime;

    private String description;

    private Boolean mainPhoto;

    private String path;

    private Boolean delete;

    private List<PhotoCommentDto> photoComments=new ArrayList<>();

    private List<PhotoLikeDto> photoLikes=new ArrayList<>();


}
