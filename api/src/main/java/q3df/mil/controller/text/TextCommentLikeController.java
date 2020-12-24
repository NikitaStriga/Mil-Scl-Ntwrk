package q3df.mil.controller.text;

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
import q3df.mil.dto.text.tcl.TextCommentLikeDto;
import q3df.mil.dto.text.tcl.TextCommentLikeSaveDto;
import q3df.mil.mapper.text.tcl.TextCommentLikeMapper;
import q3df.mil.service.TextCommentLikeService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users/{id}/texts/{textId}/textcomment/{textCommentId}/textcommentlike")
public class TextCommentLikeController {


    private final TextCommentLikeService textCommentLikeService;

    public TextCommentLikeController(TextCommentLikeService textCommentLikeService) {
        this.textCommentLikeService = textCommentLikeService;
    }


    @ApiOperation(value = "Save text comment like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Like was successfully saved")
    })
    @PostMapping
    public ResponseEntity<TextCommentLikeDto> saveText(@Valid @RequestBody TextCommentLikeSaveDto textCommentLikeSaveDto) {
        TextCommentLikeDto savedTextCommentLike = textCommentLikeService.saveTextCommentLike(textCommentLikeSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedTextCommentLike.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedTextCommentLike, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Delete text comment like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Like was successfully delete"),
            @ApiResponse(code = 404, message = "If text comment like not found")
    })
    @DeleteMapping("/{textCommentLikeId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textCommentLikeId) {
        textCommentLikeService.deleteCommentLikeById(textCommentLikeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
