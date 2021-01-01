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

import static q3df.mil.exception.ExceptionConstants.TEXT_COMMENT_NF;

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


    /**
     * save text comment
     * @param textCommentSaveDto new text comment
     * @return saved text comment
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextCommentDto saveTextComment(TextCommentSaveDto textCommentSaveDto) {
        TextComment textComment = textCommentSaveMapper.fromDto(textCommentSaveDto);
        TextComment savedText = textCommentRepository.save(textComment);
        return textCommentMapper.toDto(savedText);
    }


    /**
     * update text comment
     * @param textCommentUpdateDto text comment
     * @return updated text comment
     */
    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextCommentDto updateTextComment(TextCommentUpdateDto textCommentUpdateDto) {
        TextComment textComment =
                textCommentRepository
                .findById(textCommentUpdateDto.getId())
                .orElseThrow(() -> new TextCommentNotFoundException(TEXT_COMMENT_NF + textCommentUpdateDto.getId()));
        textComment.setComment(textCommentUpdateDto.getComment());
        return textCommentMapper.toDto(textComment);
    }


    /**
     * delete text comment
     * @param id text comment id
     */
    @Override
    public void deleteTextCommentById(Long id) {
        try{
            textCommentRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TextCommentNotFoundException(TEXT_COMMENT_NF+ id);
        }
    }

}
