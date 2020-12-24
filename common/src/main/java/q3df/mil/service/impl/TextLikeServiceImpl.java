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

    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextLikeDto saveTextLike(TextLikeSaveDto textLikeDto) {
        TextLike textLike = textLikeSaveMapper.fromDto(textLikeDto);
        TextLike savedTextLike = textLikeRepository.save(textLike);
        return textLikeMapper.toDto(savedTextLike);
    }


    @Override
    public void deleteTextLikeById(Long id) {
        try {
            textLikeRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TextLikeNotFoundException("Like with id " + id + " doesn't exist!");
        }
    }
}
