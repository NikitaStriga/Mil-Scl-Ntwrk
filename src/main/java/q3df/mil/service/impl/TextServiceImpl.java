package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.text.t.TextDto;
import q3df.mil.dto.text.t.TextSaveDto;
import q3df.mil.dto.text.t.TextUpdateDto;
import q3df.mil.entities.text.Text;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.mapper.text.t.TextMapper;
import q3df.mil.mapper.text.t.TextSaveMapper;
import q3df.mil.repository.TextRepository;
import q3df.mil.service.TextService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TextServiceImpl implements TextService {

    private final TextMapper textMapper;
    private final TextSaveMapper textSaveMapper;
    private final TextRepository textRepository;

    public TextServiceImpl(TextMapper textMapper, TextSaveMapper textSaveMapper, TextRepository textRepository) {
        this.textMapper = textMapper;
        this.textSaveMapper = textSaveMapper;
        this.textRepository = textRepository;
    }


    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<TextDto> findTextsByUserIdInDescOrder(Long id) {
        List<Text> texts = textRepository.findTextByUserIdWithDescOrder(id);
        List<TextDto> textDtos = texts.stream().map(textMapper::toDto).collect(Collectors.toList());
        if(textDtos.isEmpty()) throw new UserNotFoundException("User with id " + id + " doesn't exist!");
        return textDtos;
    }


    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextDto saveText(TextSaveDto textSaveDto) {
        Text text = textSaveMapper.fromDto(textSaveDto);
        Text savedText = textRepository.save(text);
        return textMapper.toDto(savedText);
    }


    @Override
    @org.springframework.transaction.annotation.Transactional
    public TextDto updateText(TextUpdateDto textUpdateDto) {
        Text text;
        try{
            text = textRepository.getOne(textUpdateDto.getId());
        }catch (EntityNotFoundException ex){
            throw new TextNotFoundException("Text with id" + textUpdateDto.getId() + " doesn't exist!");
        }
        text.setText(textUpdateDto.getText());
        return textMapper.toDto(text);
    }


    @Override
    public void deleteTextById(Long id) {
        try{
            textRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new TextNotFoundException("Text with id " + id + " doesn't exist!");
        }
    }





}
