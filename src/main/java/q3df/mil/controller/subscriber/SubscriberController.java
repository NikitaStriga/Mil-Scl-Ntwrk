package q3df.mil.controller.subscriber;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.dto.subscriber.SubscriberDto;
import q3df.mil.service.SubscriberService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("users/{id}/subscribers")
public class SubscriberController {


    private final SubscriberService subscriberService;


    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }


    @ApiOperation(value = "Show subscriber list of user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of user subscribers")
    })
    @GetMapping
    public ResponseEntity<List<SubscriberDto>> showUserFriends(@PathVariable Long id){
        return ResponseEntity.ok(subscriberService.findUserSubscribers(id));
    }


    @ApiOperation(value = "Add subscriber")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Subscriber was successfully added"),
            @ApiResponse(code = 404, message = "Subscriber almost exist in friend- or subscriber list, or either of both doesn't exist")
    })
    @PostMapping
    public ResponseEntity<?> addSubscriberToUser(@PathVariable Long id, @Valid @NotNull @Positive Long friendId){
        subscriberService.addSubscriber(id,friendId);
        return new ResponseEntity<>("Subscriber was added!",HttpStatus.CREATED);
    }


    @ApiOperation(value = "Delete subscriber")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "Subscriber was successfully deleted"),
            @ApiResponse(code = 404, message = "Subscriber doesn't exist in subscriber list of user")
    })
    @DeleteMapping
    public ResponseEntity<?> deleteSubscriberFromUser(@PathVariable Long id, @Valid @NotNull @Positive Long friendId ){
        subscriberService.deleteSubscriber(id,friendId);
        return new ResponseEntity<>("Subscriber was deleted!",HttpStatus.NO_CONTENT);
    }

}
