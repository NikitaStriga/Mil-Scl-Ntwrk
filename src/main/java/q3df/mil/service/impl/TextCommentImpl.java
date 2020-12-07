package q3df.mil.service.impl;

import org.springframework.stereotype.Service;
import q3df.mil.dto.text.TextCommentDto;
import q3df.mil.entities.text.TextComment;
import q3df.mil.mapper.text.TextCommentMapper;
import q3df.mil.repository.TextCommentRepository;
import q3df.mil.service.TextCommentService;


@Service
public class TextCommentImpl implements TextCommentService {


    private final TextCommentRepository textCommentRepository;
    private final TextCommentMapper textCommentMapper;

    public TextCommentImpl(TextCommentRepository textCommentRepository, TextCommentMapper textCommentMapper) {
        this.textCommentRepository = textCommentRepository;
        this.textCommentMapper = textCommentMapper;
    }


    @Override
    public TextCommentDto saveTextComment(TextCommentDto textDto) {
        TextComment textComment = textCommentMapper.fromDto(textDto);
        TextComment savedText = textCommentRepository.save(textComment);
        return textCommentMapper.toDto(savedText);
    }

    @Override
    public void deleteTextCommentById(Long id) {
        textCommentRepository.deleteById(id);
    }

    @Override
    public TextCommentDto updateTextComment(TextCommentDto textDto) {
        TextComment textComment = textCommentMapper.fromDto(textDto);
        TextComment savedText = textCommentRepository.save(textComment);
        return textCommentMapper.toDto(savedText);
    }
}
