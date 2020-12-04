package q3df.mil.dto.text;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TextLikeDto {

    private Long id;

    //Text
    private Long textId;

    //User
    private Long userId;

    //Username
    private String username;

    //Surname
    private String surname;

}
