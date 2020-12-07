package q3df.mil.controller.photo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.photo.PhotoCommentLikeDto;
import q3df.mil.service.PhotoCommentLikeService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/photos/{photoid}/photocomments/{photocommentid}/photocommentlikes")
public class PhotoCommentLikeController {

    private final PhotoCommentLikeService photoCommentLikeService;


    public PhotoCommentLikeController(PhotoCommentLikeService photoCommentLikeService) {
        this.photoCommentLikeService = photoCommentLikeService;
    }

    @PostMapping
    public ResponseEntity<?> saveText(PhotoCommentLikeDto photoCommentLikeDto){
        PhotoCommentLikeDto savedPhotoCommentLike = photoCommentLikeService.savePhotoCommentLike(photoCommentLikeDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPhotoCommentLike.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/{photocommentlikeid}")
    public ResponseEntity<?> deleteText(@PathVariable Long photocommentlikeid){
        photoCommentLikeService.deleteCommentLikeById(photocommentlikeid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
