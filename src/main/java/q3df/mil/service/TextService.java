package q3df.mil.service;

import q3df.mil.dto.text.t.TextDto;
import q3df.mil.dto.text.t.TextSaveDto;
import q3df.mil.dto.text.t.TextUpdateDto;
import q3df.mil.entities.text.Text;

import java.util.List;

public interface TextService {

    List<TextDto> findTextsByUserIdInDescOrder(Long id);
    TextDto saveText(TextSaveDto textSaveDto);
    void deleteTextById(Long id);
    TextDto updateText(TextUpdateDto textUpdateDto);


}
