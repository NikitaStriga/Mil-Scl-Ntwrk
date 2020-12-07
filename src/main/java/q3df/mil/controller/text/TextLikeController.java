package q3df.mil.controller.text;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import q3df.mil.dto.text.TextLikeDto;
import q3df.mil.service.TextLikeService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/texts/{textid}/textlike")
public class TextLikeController {


    private final TextLikeService textLikeService;

    public TextLikeController(TextLikeService textLikeService) {
        this.textLikeService = textLikeService;
    }

    @PostMapping
    public ResponseEntity<?> saveText(TextLikeDto textLikeDto){
        TextLikeDto savedTextLike = textLikeService.saveTextLike(textLikeDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedTextLike.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/{textLikeId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textLikeId){
        textLikeService.deleteTextLikeById(textLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
