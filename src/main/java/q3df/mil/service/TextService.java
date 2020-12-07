package q3df.mil.service;

import q3df.mil.dto.text.TextDto;

public interface TextService {

//    TextDto findById(Long id);
    TextDto saveText(TextDto textDto);
    void deleteTextById(Long id);
    TextDto updateText(TextDto textDto);


}
