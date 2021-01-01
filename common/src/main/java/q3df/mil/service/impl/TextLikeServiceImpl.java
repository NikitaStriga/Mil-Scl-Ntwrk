package q3df.mil.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.text.tl.TextLikeDto;
import q3df.mil.dto.text.tl.TextLikeSaveDto;
import q3df.mil.entities.text.TextLike;
import q3df.mil.exception.TextLikeNotFoundException;
import q3df.mil.mapper.text.tl.TextLikeMapper;
import q3df.mil.mapper.text.tl.TextLikeSaveMapper;
import q3df.mil.repository.TextLikeRepository;
import q3df.mil.service.TextLikeService;

import static q3df.mil.exception.ExceptionConstants.TEXT_LIKE_NF;

@Service
public class TextLikeServiceImpl implements TextLikeService {

    private final TextLikeRepository textLikeRepository;
    private final TextLikeMapper textLikeMapper;
    private final TextLikeSaveMapper textLikeSaveMapper;

    @Autowired
    public TextLikeServiceImpl(TextLikeRepository textLikeRepository, TextLikeMapper textLikeMapper, TextLikeSaveMapper textLikeSaveMapper) {
        this.textLikeRepository = textLikeRepository;
        this.textLikeMapper = textLikeMapper;
        this.textLikeSaveMapper = textLikeSaveMapper;
    }

    /**
     * save text like
     * @param textLikeDto new text like
     * @return saved text like
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextLikeDto saveTextLike(TextLikeSaveDto textLikeDto) {
        TextLike textLike = textLikeSaveMapper.fromDto(textLikeDto);
        TextLike savedTextLike = textLikeRepository.save(textLike);
        return textLikeMapper.toDto(savedTextLike);
    }


    /**
     * delete text like
     * @param id text like id
     */
    @Override
    public void deleteTextLikeById(Long id) {
        try {
            textLikeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TextLikeNotFoundException(TEXT_LIKE_NF + id);
        }
    }
}
