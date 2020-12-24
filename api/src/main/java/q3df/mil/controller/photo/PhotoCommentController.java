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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.photo.pc.PhotoCommentDto;
import q3df.mil.dto.photo.pc.PhotoCommentSaveDto;
import q3df.mil.dto.photo.pc.PhotoCommentUpdateDto;
import q3df.mil.service.PhotoCommentService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users/{id}/photos/{photoid}/photocomments")
public class PhotoCommentController {

    private final PhotoCommentService photoCommentService;

    public PhotoCommentController(PhotoCommentService photoCommentService) {
        this.photoCommentService = photoCommentService;
    }


    @ApiOperation(value = "Save photo comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Photo comment was successfully saved"),
            @ApiResponse(code = 404, message = "If the user who wants to save the photo comment or photo is not found")
    })
    @PostMapping
    public ResponseEntity<PhotoCommentDto> saveText(@Valid @RequestBody PhotoCommentSaveDto photoCommentSaveDto) {
        PhotoCommentDto savedPhotoComment = photoCommentService.savePhotoComment(photoCommentSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedPhotoComment.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedPhotoComment, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update photo comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Photo comment was successfully updated"),
            @ApiResponse(code = 404, message = "Photo comment not found")
    })
    @PutMapping("/{photoCommentId}")
    public ResponseEntity<PhotoCommentDto> updateText(@Valid @RequestBody PhotoCommentUpdateDto photoCommentUpdateDto) {
        return ResponseEntity.ok(photoCommentService.updatePhotoComment(photoCommentUpdateDto));
    }


    @ApiOperation(value = "Delete photo comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Photo comment was successfully deleted"),
            @ApiResponse(code = 404, message = "Photo comment not found")
    })
    @DeleteMapping("/{photoCommentId}")
    public ResponseEntity<?> deleteText(@PathVariable Long photoCommentId) {
        photoCommentService.deletePhotoCommentById(photoCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
