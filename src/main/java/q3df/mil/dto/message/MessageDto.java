package q3df.mil.dto.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MessageDto {

    private Long id;

    private Long fromWhoId;
    private String fromWhoName;
    private String fromWhoSurname;

    private Long toWhoId;
    private String toWhoName;
    private String toWhoSurname;

    private LocalDateTime created;

    private LocalDateTime updateTime;

    private String text;

    private Boolean delete;

    private Long dialogId;

}
