package q3df.mil.dto.dialog;


import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class DialogDto {

    private Long id;

    private LocalDateTime created;

    private Long firstUserId;
    private String firstUserName;
    private String firstUserSurname;

    private Long secondUserId;
    private String secondUserName;
    private String secondUserSurname;

}
