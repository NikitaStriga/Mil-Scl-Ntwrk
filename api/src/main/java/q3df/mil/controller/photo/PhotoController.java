package q3df.mil.controller.photo;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.dto.photo.p.PhotoDto;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.dto.photo.p.PhotoUpdateDto;
import q3df.mil.util.CustomPermission;
import q3df.mil.util.SupClass;
import q3df.mil.service.PhotoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/users/{id}/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final SupClass supClass;
    private final CustomPermission customPermission;

    @Autowired
    public PhotoController(PhotoService photoService, SupClass supClass, CustomPermission customPermission) {
        this.photoService = photoService;
        this.supClass = supClass;
        this.customPermission = customPermission;
    }


    @ApiOperation(value = "Find photo by Id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Photo was founded"),
            @ApiResponse(code = 404, message = "Photo not found")
    })
    public ResponseEntity<PhotoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(photoService.findById(id));
    }

    @ApiOperation(value = "Save photo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Photo was successfully saved"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If the user who wants to save the photo is not found")
    })
    @PostMapping
    public ResponseEntity<PhotoDto> saveText(@Valid @RequestBody PhotoSaveDto photoSaveDto,
                                             @PathVariable Long id,
                                             HttpServletRequest request) {
        customPermission.checkPermission(request, id);
        PhotoDto savedPhoto = photoService.savePhoto(photoSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedPhoto.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedPhoto, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update photo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Photo was successfully updated"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If photo not found")
    })
    @PutMapping("/{photoId}")
    public ResponseEntity<PhotoDto> updateText(@Valid @RequestBody PhotoUpdateDto photoUpdateDto,
                                               @PathVariable Long id,
                                               HttpServletRequest request
    ) {
        customPermission.checkPermission(request, id);
        return ResponseEntity.ok(photoService.updatePhoto(photoUpdateDto));
    }


    @ApiOperation(value = "Delete photo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Photo was successfully deleted"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If photo not found")
    })
    @DeleteMapping("/{photoId}")
    public ResponseEntity<?> deleteText(@PathVariable Long photoId, @PathVariable Long id, HttpServletRequest request) {
        customPermission.checkPermission(request, id);
        photoService.deletePhotoById(photoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
