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
import q3df.mil.dto.text.TextCommentDto;
import q3df.mil.service.TextCommentService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/texts/{textId}/textComment")
public class TextCommentController {

    private final TextCommentService textCommentService;

    public TextCommentController(TextCommentService textCommentService) {
        this.textCommentService = textCommentService;
    }


    @PostMapping
    public ResponseEntity<?> saveText(TextCommentDto textCommentDto){
        TextCommentDto savedTextComment = textCommentService.saveTextComment(textCommentDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedTextComment.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @PutMapping("/{textCommentId}")
    public ResponseEntity<TextCommentDto> updateText(@PathVariable Long textCommentId, @RequestBody TextCommentDto textCommentDto){
        return ResponseEntity.ok(textCommentService.updateTextComment(textCommentDto));
    }



    @DeleteMapping("/{textCommentId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textId){
        textCommentService.deleteTextCommentById(textId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
