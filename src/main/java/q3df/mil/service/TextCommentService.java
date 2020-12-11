package q3df.mil.service;

import q3df.mil.dto.text.tc.TextCommentDto;
import q3df.mil.dto.text.tc.TextCommentSaveDto;
import q3df.mil.dto.text.tc.TextCommentUpdateDto;


public interface TextCommentService {

    TextCommentDto saveTextComment(TextCommentSaveDto textCommentSaveDto);

    void deleteTextCommentById(Long id);

    TextCommentDto updateTextComment(TextCommentUpdateDto textCommentUpdateDto);


}
