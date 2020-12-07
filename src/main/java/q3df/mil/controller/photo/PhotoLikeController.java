package q3df.mil.controller.photo;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.photo.PhotoLikeDto;
import q3df.mil.service.PhotoLikeService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/photos/{photoid}/photolikes")
public class PhotoLikeController {


    private final PhotoLikeService photoLikeService;


    public PhotoLikeController(PhotoLikeService photoLikeService) {
        this.photoLikeService = photoLikeService;
    }


    @PostMapping
    public ResponseEntity<?> saveText(PhotoLikeDto photoLikeDto){
        PhotoLikeDto savedPhotoLike = photoLikeService.savePhotoLike(photoLikeDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPhotoLike.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/{photolikeid}")
    public ResponseEntity<?> deleteText(@PathVariable Long photolikeid){
        photoLikeService.deletePhotoLikeById(photolikeid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
