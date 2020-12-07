package q3df.mil.service;

import q3df.mil.dto.text.TextLikeDto;

public interface TextLikeService {
    TextLikeDto saveTextLike(TextLikeDto textLikeDto);
    void deleteTextLikeById(Long id);

}
