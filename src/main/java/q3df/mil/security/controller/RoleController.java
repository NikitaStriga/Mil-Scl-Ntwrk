package q3df.mil.security.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.entities.enums.SystemRoles;
import q3df.mil.service.UserService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final UserService userService;

    @Autowired
    public RoleController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "Add role to user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Role was successfully added"),
            @ApiResponse(code = 400, message = "Invalid role"),
            @ApiResponse(code = 404, message = "User with current id not found")
    })
    @PostMapping
    public ResponseEntity<String> addRoleToUser(Long userId, SystemRoles roles){
        userService.addRoleToUser(userId,roles);
        return ResponseEntity.ok("Role was successfully added!");
    }


    @ApiOperation(value = "Delete role from user", notes = "All params are essential")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Role was successfully deleted"),
            @ApiResponse(code = 400, message = "Invalid role"),
            @ApiResponse(code = 404, message = "User with current id not found")
    })
    @DeleteMapping
    public ResponseEntity<String> removeRoleFromUser(Long userId, SystemRoles roles){
        userService.deleteRoleFromUser(userId,roles);
        return new ResponseEntity<>("Role was successfully deleted!", HttpStatus.NO_CONTENT);

    }




    @Data
    @AllArgsConstructor
    @PropertySource("classpath:messages.properties")
    public class HelperRoleClass{
        @NotNull(message = "{friendId.empty}")
        @Positive(message = "{friendId.positive}")
        private String userId;
        private SystemRoles systemRoles;
    }


}
