package q3df.mil.controller.friend;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.dto.friend.FriendDto;
import q3df.mil.service.FriendService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("users/{id}/friends")
public class FriendController {

    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @ApiOperation(value = "Show friend list of user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of user friends")
    })
    @GetMapping
    public ResponseEntity<List<FriendDto>> showUserFriends(@PathVariable Long id){
        return ResponseEntity.ok(friendService.findUserFriends(id));
    }


    @ApiOperation(value = "Add friend")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Friend was successfully added"),
            @ApiResponse(code = 404, message = "Friend almost exist in friend- or friend list, or either of both doesn't exist")
    })
    @PostMapping
    public ResponseEntity<String> addFriendToUser(@PathVariable Long id, @Valid @NotNull @Positive Long friendId){
        friendService.addFriendToUser(id,friendId);
        return ResponseEntity.ok("Friend was added!");
    }


    @ApiOperation(value = "Delete friend")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Friend was successfully deleted"),
            @ApiResponse(code = 404, message = "Friend doesn't exist in subscriber list of user")
    })
    @DeleteMapping
    public ResponseEntity<String> deleteFriendFromUser(@PathVariable Long id, @Valid @NotNull @Positive Long friendId ){
        friendService.deleteFriendFromUser(id,friendId);
        return ResponseEntity.ok("Friend was deleted!");
    }

}
