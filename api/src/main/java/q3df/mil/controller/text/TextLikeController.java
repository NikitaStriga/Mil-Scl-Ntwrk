package q3df.mil.controller.text;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import q3df.mil.dto.text.tl.TextLikeDto;
import q3df.mil.dto.text.tl.TextLikeSaveDto;
import q3df.mil.service.TextLikeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/{id}/texts/{textId}/textLike")
public class TextLikeController {


    private final TextLikeService textLikeService;

    @Autowired
    public TextLikeController(TextLikeService textLikeService) {
        this.textLikeService = textLikeService;
    }


    @ApiOperation(value = "Save text like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Like was successfully saved"),
            @ApiResponse(code = 404, message = "If the user who wants to save like to text is not found")
    })
    @PostMapping
    public ResponseEntity<TextLikeDto> saveText(@Valid @RequestBody TextLikeSaveDto textLikeSaveDto) {
        TextLikeDto savedTextLike = textLikeService.saveTextLike(textLikeSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedTextLike.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedTextLike, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Delete text like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Like was successfully deleted"),
            @ApiResponse(code = 404, message = "If text like is not found! ")
    })
    @DeleteMapping("/{textLikeId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textLikeId) {
        textLikeService.deleteTextLikeById(textLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
