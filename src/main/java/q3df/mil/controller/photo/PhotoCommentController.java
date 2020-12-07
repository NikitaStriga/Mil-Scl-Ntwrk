package q3df.mil.controller.photo;

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
import q3df.mil.dto.photo.PhotoCommentDto;
import q3df.mil.service.PhotoCommentService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/photos/{photoid}/photocomments")
public class PhotoCommentController {



    private final PhotoCommentService photoCommentService;

    public PhotoCommentController(PhotoCommentService photoCommentService) {
        this.photoCommentService = photoCommentService;
    }


    @PostMapping
    public ResponseEntity<?> saveText(PhotoCommentDto photoCommentDto){
        PhotoCommentDto savedPhotoComment = photoCommentService.savePhotoComment(photoCommentDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPhotoComment.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @PutMapping("/{photoCommentId}")
    public ResponseEntity<PhotoCommentDto> updateText(@PathVariable Long photoCommentId, @RequestBody PhotoCommentDto photoCommentDto){
        return ResponseEntity.ok(photoCommentService.updatePhotoComment(photoCommentDto));
    }



    @DeleteMapping("/{photoCommentId}")
    public ResponseEntity<?> deleteText(@PathVariable Long photoCommentId){
        photoCommentService.deletePhotoCommentById(photoCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
