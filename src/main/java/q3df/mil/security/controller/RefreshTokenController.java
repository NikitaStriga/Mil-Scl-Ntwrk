package q3df.mil.security.controller;

import io.jsonwebtoken.Claims;
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

    @GetMapping
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request){

        //get claims from request attribute
        Claims claims = (Claims ) request.getAttribute("claims");

        //return response with new refreshed token
        return ResponseEntity.ok(AuthResponse
                .builder()
                .username(claims.getSubject())
                .token(tokenUtils.refreshToken(claims))
                .build());
    }
}
