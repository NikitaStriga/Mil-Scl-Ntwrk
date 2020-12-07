package q3df.mil.controller.text;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.text.TextDto;
import q3df.mil.service.TextService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/texts")
public class TextController {

    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }


    @PostMapping
    public ResponseEntity<?> saveText(TextDto textDto){
        TextDto savedDto = textService.saveText(textDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedDto.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @PutMapping("/{textId}")
    public ResponseEntity<TextDto> updateText(@PathVariable Long textId, @RequestBody TextDto textDto){
        return ResponseEntity.ok(textService.updateText(textDto));
    }



    @DeleteMapping("/{textId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textId){
        textService.deleteTextById(textId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
