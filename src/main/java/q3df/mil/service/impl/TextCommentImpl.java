package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.text.tc.TextCommentDto;
import q3df.mil.dto.text.tc.TextCommentSaveDto;
import q3df.mil.dto.text.tc.TextCommentUpdateDto;
import q3df.mil.entities.text.TextComment;
import q3df.mil.exception.TextCommentNotFoundException;
import q3df.mil.mapper.text.tc.TextCommentMapper;
import q3df.mil.mapper.text.tc.TextCommentSaveMapper;
import q3df.mil.repository.TextCommentRepository;
import q3df.mil.service.TextCommentService;

import javax.persistence.EntityNotFoundException;


@Service
public class TextCommentImpl implements TextCommentService {


    private final TextCommentRepository textCommentRepository;
    private final TextCommentMapper textCommentMapper;
    private final TextCommentSaveMapper textCommentSaveMapper;

    public TextCommentImpl(TextCommentRepository textCommentRepository, TextCommentMapper textCommentMapper, TextCommentSaveMapper textCommentSaveMapper) {
        this.textCommentRepository = textCommentRepository;
        this.textCommentMapper = textCommentMapper;
        this.textCommentSaveMapper = textCommentSaveMapper;
    }


    @Override
    public TextCommentDto saveTextComment(TextCommentSaveDto textCommentSaveDto) {
        TextComment textComment = textCommentSaveMapper.fromDto(textCommentSaveDto);
        TextComment savedText = textCommentRepository.save(textComment);
        return textCommentMapper.toDto(savedText);
    }


    @Override
    public TextCommentDto updateTextComment(TextCommentUpdateDto textCommentUpdateDto) {
        TextComment textComment;
        try{
            textComment = textCommentRepository.getOne(textCommentUpdateDto.getId());
        }catch (EntityNotFoundException ex){
            throw  new TextCommentNotFoundException("Text comment with id " + textCommentUpdateDto.getId() + " doesn't exist!");
        }
        textComment.setComment(textCommentUpdateDto.getComment());
        return textCommentMapper.toDto(textComment);
    }


    @Override
    public void deleteTextCommentById(Long id) {
        try{
            textCommentRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TextCommentNotFoundException("Text comment with id " + id + " doesn't exist");
        }
    }
}
