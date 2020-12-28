package q3df.mil.controller.photo;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.photo.pl.PhotoLikeDto;
import q3df.mil.dto.photo.pl.PhotoLikeSaveDto;
import q3df.mil.service.PhotoLikeService;

import java.net.URI;

@RestController
@RequestMapping("/users/{id}/photos/{photoid}/photolikes")
public class PhotoLikeController {


    private final PhotoLikeService photoLikeService;


    public PhotoLikeController(PhotoLikeService photoLikeService) {
        this.photoLikeService = photoLikeService;
    }


    @ApiOperation(value = "Save photo like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Photo like was created"),
            @ApiResponse(code = 404, message = "User or photo not found")
    })
    @PostMapping
    public ResponseEntity<PhotoLikeDto> savePhotoLike(PhotoLikeSaveDto photoLikeSaveDto) {
        PhotoLikeDto savedPhotoLike = photoLikeService.savePhotoLike(photoLikeSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedPhotoLike.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedPhotoLike, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete photo like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Photo like was deleted"),
            @ApiResponse(code = 404, message = "Photo like not found")
    })
    @DeleteMapping("/{photoLikeId}")
    public ResponseEntity<?> deletePhotoLike(@PathVariable Long photoLikeId) {
        photoLikeService.deletePhotoLikeById(photoLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
