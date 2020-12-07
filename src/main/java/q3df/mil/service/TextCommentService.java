package q3df.mil.service;

import q3df.mil.dto.text.TextCommentDto;


public interface TextCommentService {

//    TextCommentDto findById(Long id);
    TextCommentDto saveTextComment(TextCommentDto textDto);
    void deleteTextCommentById(Long id);
    TextCommentDto updateTextComment(TextCommentDto textDto);


}
