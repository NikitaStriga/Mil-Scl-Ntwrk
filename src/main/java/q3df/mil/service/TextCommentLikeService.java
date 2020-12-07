package q3df.mil.service;

import q3df.mil.dto.text.TextCommentLikeDto;

public interface TextCommentLikeService {
    TextCommentLikeDto saveTextCommentLike(TextCommentLikeDto textCommentLikeDto);
    void deleteCommentLikeById(Long id);
}
