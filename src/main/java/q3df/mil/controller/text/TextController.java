package q3df.mil.controller.text;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import q3df.mil.dto.text.t.TextDto;
import q3df.mil.dto.text.t.TextSaveDto;
import q3df.mil.dto.text.t.TextUpdateDto;
import q3df.mil.myfeatures.SupClass;
import q3df.mil.service.TextService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/texts")
public class TextController {

    private final TextService textService;
    private final SupClass supClass;

    public TextController(TextService textService, SupClass supClass) {
        this.textService = textService;
        this.supClass = supClass;
    }



    @ApiOperation(value = "Return all user texts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of texts"),
            @ApiResponse(code = 404, message = "Text with this user id is not found. It means that user is doesn't exist")
    })
    @GetMapping
    public ResponseEntity<List<TextDto>> findAllUserTexts(@PathVariable Long id){
        return ResponseEntity.ok(textService.findTextsByUserIdInDescOrder(id));
    }


    @ApiOperation(value = "Save text")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Text was successfully saved"),
            @ApiResponse(code = 403, message = "No permission to execute the operation")
    })
    @PostMapping
    public ResponseEntity<TextDto> saveText(@Valid @RequestBody TextSaveDto textSaveDto, @PathVariable Long id, HttpServletRequest request){
        supClass.checkPermission(request,id);
        TextDto savedDto = textService.saveText(textSaveDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(savedDto.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
        return new ResponseEntity<>(savedDto,HttpStatus.CREATED);
    }



    @ApiOperation(value = "Update text")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Text was successfully updated"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If the user who wants to save the text is not found")
    })
    @PutMapping("/{textId}")
    public ResponseEntity<TextDto> updateText(@Valid @RequestBody TextUpdateDto textUpdateDto, @PathVariable Long id, HttpServletRequest request){
        supClass.checkPermission(request,id);
        return ResponseEntity.ok(textService.updateText(textUpdateDto));
    }



    @ApiOperation(value = "Delete text")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Text was successfully deleted"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If the user who wants to save the text or text is not found")
    })
    @DeleteMapping("/{textId}")
    public ResponseEntity<?> deleteText(@PathVariable Long textId, @PathVariable Long id, HttpServletRequest request){
        supClass.checkPermission(request,id);
        textService.deleteTextById(textId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
