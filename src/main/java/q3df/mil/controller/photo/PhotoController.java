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
import q3df.mil.dto.photo.PhotoDto;
import q3df.mil.service.PhotoService;

import java.net.URI;


@RestController
@RequestMapping("/users/{id}/photos")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<?> saveText(PhotoDto photoDto){
        PhotoDto savedPhoto = photoService.savePhoto(photoDto);
        URI location=
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedPhoto.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }



    @PutMapping("/{photoId}")
    public ResponseEntity<PhotoDto> updateText(@PathVariable Long photoId, @RequestBody PhotoDto photoDto){
        return ResponseEntity.ok(photoService.savePhoto(photoDto));
    }



    @DeleteMapping("/{photoId}")
    public ResponseEntity<?> deleteText(@PathVariable Long photoId){
        photoService.deletePhotoById(photoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
