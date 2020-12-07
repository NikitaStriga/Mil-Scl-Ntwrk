package q3df.mil.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import q3df.mil.dto.text.TextDto;
import q3df.mil.entities.text.Text;
import q3df.mil.exception.TextNotFoundException;
import q3df.mil.mapper.text.TextMapper;
import q3df.mil.repository.TextRepository;
import q3df.mil.service.TextService;

@Service
public class TextServiceImpl implements TextService {

    private final TextMapper textMapper;
    private final TextRepository textRepository;

    public TextServiceImpl(TextMapper textMapper, TextRepository textRepository) {
        this.textMapper = textMapper;
        this.textRepository = textRepository;
    }



    @Override
    public TextDto saveText(TextDto textDto) {
        Text text = textMapper.fromDto(textDto);
        Text savedText = textRepository.save(text);
        return textMapper.toDto(savedText);
    }


    @Override
    public void deleteTextById(Long id) {
        try{
            textRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new TextNotFoundException("Text with id " + id + " doesn't exist!");
        }
    }


    @Override
    public TextDto updateText(TextDto textDto) {
        Text text = textMapper.fromDto(textDto);
        Text savedText = textRepository.save(text);
        return textMapper.toDto(savedText);
    }


}
