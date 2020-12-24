package q3df.mil.service;

import q3df.mil.dto.text.tcl.TextCommentLikeDto;
import q3df.mil.dto.text.tcl.TextCommentLikeSaveDto;

public interface TextCommentLikeService {

    TextCommentLikeDto saveTextCommentLike(TextCommentLikeSaveDto textCommentLikeSaveDto);

    void deleteCommentLikeById(Long id);
}
