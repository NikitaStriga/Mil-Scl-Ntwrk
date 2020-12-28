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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.photo.pcl.PhotoCommentLikeDto;
import q3df.mil.dto.photo.pcl.PhotoCommentLikeSaveDto;
import q3df.mil.service.PhotoCommentLikeService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users/{id}/photos/{photoid}/photocomments/{photocommentid}/photocommentlikes")
public class PhotoCommentLikeController {

    private final PhotoCommentLikeService photoCommentLikeService;


    public PhotoCommentLikeController(PhotoCommentLikeService photoCommentLikeService) {
        this.photoCommentLikeService = photoCommentLikeService;
    }


    @ApiOperation(value = "Save photo comment like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Photo comment like was created"),
            @ApiResponse(code = 404, message = "User or photo comment  not found")
    })
    @PostMapping
    public ResponseEntity<PhotoCommentLikeDto> saveText(@Valid @RequestBody PhotoCommentLikeSaveDto photoCommentLikeSaveDto) {
        PhotoCommentLikeDto savedPhotoCommentLike = photoCommentLikeService.savePhotoCommentLike(photoCommentLikeSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedPhotoCommentLike.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedPhotoCommentLike, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Delete photo comment like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Photo comment like was deleted"),
            @ApiResponse(code = 404, message = "Photo comment like not found")
    })
    @DeleteMapping("/{photoCommentLikeId}")
    public ResponseEntity<?> deletePhotoCommentLike(@PathVariable Long photoCommentLikeId) {
        photoCommentLikeService.deleteCommentLikeById(photoCommentLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
