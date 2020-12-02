package q3df.mil.dto.dialog_message_dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageDto {

    private Long id;

    private Long fromWhoId;
    private String fromWhoName;
    private String fromWhoSurname;

    private Long toWhoId;
    private String toWhoName;
    private String toWhoSurname;

    private LocalDateTime created;

    private Boolean edited;

    private String text;

    private Boolean delete;

    private Long dialogId;

}
