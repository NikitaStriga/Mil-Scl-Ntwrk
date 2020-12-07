package q3df.mil.controller.text;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.text.TextCommentLikeDto;
import q3df.mil.mapper.text.TextCommentLikeMapper;
import q3df.mil.service.TextCommentLikeService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/texts/{textId}/textcomment/{textcommentid}/textcommentlike")
public class TextCommentLikeController {


    private final TextCommentLikeService textCommentLikeService;
    private final TextCommentLikeMapper textCommentLikeMapper;

    public TextCommentLikeController(TextCommentLikeService textCommentLikeService,
                                     TextCommentLikeMapper textCommentLikeMapper) {
        this.textCommentLikeService = textCommentLikeService;
        this.textCommentLikeMapper = textCommentLikeMapper;
    }


    @PostMapping
    public ResponseEntity<?> saveText(TextCommentLikeDto textCommentLikeDto){
        TextCommentLikeDto savedTextCommentLike = textCommentLikeService.saveTextCommentLike(textCommentLikeDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedTextCommentLike.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @DeleteMapping("/{textCommentLikeId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textCommentLikeId){
        textCommentLikeService.deleteCommentLikeById(textCommentLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
