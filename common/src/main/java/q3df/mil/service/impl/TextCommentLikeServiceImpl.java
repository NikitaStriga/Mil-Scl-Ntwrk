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


    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextCommentLikeDto saveTextCommentLike(TextCommentLikeSaveDto textCommentLikeDto) {
        TextCommentLike textCommentLike = textCommentLikeSaveMapper.fromDto(textCommentLikeDto);
        TextCommentLike savedTextCommentLike = textCommentLikeRepository.save(textCommentLike);
        return textCommentLikeMapper.toDto(savedTextCommentLike);
    }

    @Override
    public void deleteCommentLikeById(Long id) {

        try{
            textCommentLikeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TextCommentLikeNotFoundException("Text comment like with id " + id + " doesn't exist!");
        }
    }
}
