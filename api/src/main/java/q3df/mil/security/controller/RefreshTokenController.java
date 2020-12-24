package q3df.mil.security.controller;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.security.model.AuthResponse;
import q3df.mil.security.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/refresh")
public class RefreshTokenController {

    private final TokenUtils tokenUtils;

    @Autowired
    public RefreshTokenController(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }


    @ApiOperation(value = "Endpoint to refresh  the token",
            notes = "If the token is valid, then a new token will be generated based on claims")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Token has been successfully refreshed!"),
            @ApiResponse(code = 401, message = "Token not valid!"),
    })
    @GetMapping
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {

        //get claims from request attribute
        Claims claims = (Claims) request.getAttribute("claims");

        //return response with new refreshed token
        return ResponseEntity.ok(AuthResponse
                .builder()
                .username(claims.getSubject())
                .token(tokenUtils.refreshToken(claims))
                .build());
    }
}
