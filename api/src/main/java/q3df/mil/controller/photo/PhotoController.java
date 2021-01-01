package q3df.mil.controller.photo;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import q3df.mil.dto.photo.p.PhotoDto;
import q3df.mil.dto.photo.p.PhotoSaveDto;
import q3df.mil.dto.photo.p.PhotoUpdateDto;
import q3df.mil.util.CustomPermission;
import q3df.mil.util.SupClass;
import q3df.mil.service.PhotoService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/users/{id}/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final CustomPermission customPermission;

    @Autowired
    public PhotoController(PhotoService photoService, CustomPermission customPermission) {
        this.photoService = photoService;
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
    @GetMapping
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
    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<Map<String, String>> savePhoto(@RequestPart MultipartFile multipartFile,
                                                         @NotNull @NotBlank @RequestPart(required = false) String description,
                                                         @PathVariable Long id,
                                                         HttpServletRequest request) {
        customPermission.checkPermission(request, id);
        String urlOfSavedImage =
                photoService.savePhoto(
                        PhotoSaveDto.builder()
                                .userId(id)
                                .description(description)
                                .build(), multipartFile);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedPhoto.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(Collections.singletonMap("url", urlOfSavedImage), HttpStatus.CREATED);
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
    public ResponseEntity<PhotoDto> updatePhoto(@Valid @RequestBody PhotoUpdateDto photoUpdateDto,
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
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId, @PathVariable Long id, HttpServletRequest request) {
        customPermission.checkPermission(request, id);
        photoService.deletePhotoById(photoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
