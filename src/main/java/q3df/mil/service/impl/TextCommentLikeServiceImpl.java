package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.text.TextCommentLikeDto;
import q3df.mil.entities.text.TextCommentLike;
import q3df.mil.mapper.text.TextCommentLikeMapper;
import q3df.mil.repository.TextCommentLikeRepository;
import q3df.mil.service.TextCommentLikeService;

@Service
public class TextCommentLikeServiceImpl implements TextCommentLikeService {


    private final TextCommentLikeRepository textCommentLikeRepository;
    private final TextCommentLikeMapper textCommentLikeMapper;

    public TextCommentLikeServiceImpl(TextCommentLikeRepository textCommentLikeRepository, TextCommentLikeMapper textCommentLikeMapper) {
        this.textCommentLikeRepository = textCommentLikeRepository;
        this.textCommentLikeMapper = textCommentLikeMapper;
    }


    @Override
    public TextCommentLikeDto saveTextCommentLike(TextCommentLikeDto textCommentLikeDto) {
        TextCommentLike textCommentLike = textCommentLikeMapper.fromDto(textCommentLikeDto);
        TextCommentLike savedTextCommentLike = textCommentLikeRepository.save(textCommentLike);
        return textCommentLikeMapper.toDto(savedTextCommentLike);
    }

    @Override
    public void deleteCommentLikeById(Long id) {
        textCommentLikeRepository.deleteById(id);
    }
}
