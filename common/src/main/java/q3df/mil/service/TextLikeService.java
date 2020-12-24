package q3df.mil.service;

import q3df.mil.dto.text.tl.TextLikeDto;
import q3df.mil.dto.text.tl.TextLikeSaveDto;

public interface TextLikeService {

    TextLikeDto saveTextLike(TextLikeSaveDto textLikeSaveDto);

    void deleteTextLikeById(Long id);

}
