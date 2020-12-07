package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.text.TextLikeDto;
import q3df.mil.entities.text.TextLike;
import q3df.mil.mapper.text.TextLikeMapper;
import q3df.mil.repository.TextLikeRepository;
import q3df.mil.service.TextLikeService;


@Service
public class TextLikeServiceImpl implements TextLikeService {

    private final TextLikeRepository textLikeRepository;
    private final TextLikeMapper textLikeMapper;

    public TextLikeServiceImpl(TextLikeRepository textLikeRepository, TextLikeMapper textLikeMapper) {
        this.textLikeRepository = textLikeRepository;
        this.textLikeMapper = textLikeMapper;
    }

    @Override
    public TextLikeDto saveTextLike(TextLikeDto textLikeDto) {
        TextLike textLike = textLikeMapper.fromDto(textLikeDto);
        TextLike savedTextLike = textLikeRepository.save(textLike);
        return textLikeMapper.toDto(savedTextLike);
    }


    @Override
    public void deleteTextLikeById(Long id) {
        textLikeRepository.deleteById(id);
    }
}
