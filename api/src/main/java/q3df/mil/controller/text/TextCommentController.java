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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.text.tc.TextCommentDto;
import q3df.mil.dto.text.tc.TextCommentSaveDto;
import q3df.mil.dto.text.tc.TextCommentUpdateDto;
import q3df.mil.service.TextCommentService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users/{id}/texts/{textId}/textComment")
public class TextCommentController {

    private final TextCommentService textCommentService;

    @Autowired
    public TextCommentController(TextCommentService textCommentService) {
        this.textCommentService = textCommentService;
    }

    @ApiOperation(value = "Save text comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Text comment was successfully saved"),
            @ApiResponse(code = 404, message = "If the user who wants to save the text or text you want to comment on is not found")
    })
    @PostMapping
    public ResponseEntity<TextCommentDto> saveText(@Valid @RequestBody TextCommentSaveDto textCommentSaveDto) {
        TextCommentDto savedTextComment = textCommentService.saveTextComment(textCommentSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedTextComment.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedTextComment, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update text comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Text comment was successfully updated"),
            @ApiResponse(code = 404, message = "If text comment is not found")
    })
    @PutMapping("/{textCommentId}")
    public ResponseEntity<TextCommentDto> updateText(@Valid @RequestBody TextCommentUpdateDto textCommentUpdateDto) {
        return ResponseEntity.ok(textCommentService.updateTextComment(textCommentUpdateDto));
    }


    @ApiOperation(value = "Delete text comment")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Text comment was successfully deleted"),
            @ApiResponse(code = 404, message = "If text comment is not found")
    })
    @DeleteMapping("/{textCommentId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textId) {
        textCommentService.deleteTextCommentById(textId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
