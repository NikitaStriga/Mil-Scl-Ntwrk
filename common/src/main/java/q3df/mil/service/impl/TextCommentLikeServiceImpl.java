package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.text.tcl.TextCommentLikeDto;
import q3df.mil.dto.text.tcl.TextCommentLikeSaveDto;
import q3df.mil.entities.text.TextCommentLike;
import q3df.mil.exception.TextCommentLikeNotFoundException;
import q3df.mil.mapper.text.tcl.TextCommentLikeMapper;
import q3df.mil.mapper.text.tcl.TextCommentLikeSaveMapper;
import q3df.mil.repository.TextCommentLikeRepository;
import q3df.mil.service.TextCommentLikeService;

import static q3df.mil.exception.ExceptionConstants.TEXT_COMMENT_LIKE_NF;

@Service
public class TextCommentLikeServiceImpl implements TextCommentLikeService {


    private final TextCommentLikeRepository textCommentLikeRepository;
    private final TextCommentLikeMapper textCommentLikeMapper;
    private final TextCommentLikeSaveMapper textCommentLikeSaveMapper;

    public TextCommentLikeServiceImpl(TextCommentLikeRepository textCommentLikeRepository, TextCommentLikeMapper textCommentLikeMapper, TextCommentLikeSaveMapper textCommentLikeSaveMapper) {
        this.textCommentLikeRepository = textCommentLikeRepository;
        this.textCommentLikeMapper = textCommentLikeMapper;
        this.textCommentLikeSaveMapper = textCommentLikeSaveMapper;
    }


    /**
     * save text comment like
     * @param textCommentLikeDto new text comment like
     * @return saved text comment like
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextCommentLikeDto saveTextCommentLike(TextCommentLikeSaveDto textCommentLikeDto) {
        TextCommentLike textCommentLike = textCommentLikeSaveMapper.fromDto(textCommentLikeDto);
        TextCommentLike savedTextCommentLike = textCommentLikeRepository.save(textCommentLike);
        return textCommentLikeMapper.toDto(savedTextCommentLike);
    }


    /**
     * delete comment like
     * @param id comment like id
     */
    @Override
    public void deleteCommentLikeById(Long id) {

        try{
            textCommentLikeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TextCommentLikeNotFoundException(TEXT_COMMENT_LIKE_NF + id);
        }
    }

}
