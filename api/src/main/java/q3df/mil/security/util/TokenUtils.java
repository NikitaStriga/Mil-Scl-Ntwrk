package q3df.mil.security.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import q3df.mil.entities.user.User;
import q3df.mil.exception.UserNotFoundException;
import q3df.mil.repository.UserRepository;
import q3df.mil.security.configuration.JwtTokenConfig;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;
import static q3df.mil.security.util.JwtConstants.CREATE_VALUE;
import static q3df.mil.security.util.JwtConstants.P_CHANGE;
import static q3df.mil.security.util.JwtConstants.ROLES;
import static q3df.mil.security.util.JwtConstants.USER_ID;


@Component
@Log4j2
@PropertySource("classpath:jwtconfig.properties")
public class TokenUtils {


    private final JwtTokenConfig jwtTokenConfig;
    private final UserRepository userRepository;

    @Autowired
    public TokenUtils(JwtTokenConfig jwtTokenConfig, UserRepository userRepository) {
        this.jwtTokenConfig = jwtTokenConfig;
        this.userRepository = userRepository;
    }


    /**
     * ->  IT SEEMS LIKE THIS METHOD IS NOT NEEDED <-
     * get username(login)
     * @param token jwtToken
     * @return login of user
     */
//    public String getUsernameFromToken(String token) {
//        return getClaimsFromToken(token).getSubject();
//    }


    /**
     * ->  IT SEEMS LIKE THIS METHOD IS NOT NEEDED <-
     * get creation time of token
     * @param token jwtToken
     * @return creation time of jwtToken
     */
//    public Date getCreatedDateFromToken(String token) {
//        return (Date) getClaimsFromToken(token).get(CREATE_VALUE);
//    }

    /**
     * ->  IT SEEMS LIKE THIS METHOD IS NOT NEEDED <-
     * get expiration date of token
     * @param token jwtToken
     * @return expiration time of jwtToken
     */
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimsFromToken(token).getExpiration();
//    }


    /**
     * get all claims which token contains
     *
     * @param token jwtToken
     * @return claims
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtTokenConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();

    }


    /**
     * check for valid of token
     *
     * @param token jwtToken
     * @return exception if token is not valid , true if the parsing was successful
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtTokenConfig.getSecret()).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            log.error(ex.getMessage());
            throw new BadCredentialsException("Invalid Json Token!", ex);
        } catch (ExpiredJwtException expiredJwtException) {
            throw expiredJwtException;
        }
    }


    /**
     * this method generate date for new token
     *
     * @return Date
     */
    private Date generateCurrentDate() {
        return new Date();
    }


    /**
     * generate expiration Date for token
     *
     * @return expiration Date of token
     */
    private Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MILLISECOND, jwtTokenConfig.getExpiration().intValue());
        return calendar.getTime();
    }


    /**
     *  ->  IT SEEMS LIKE THIS METHOD IS NOT NEEDED <-
     * by this method we checked expiration date of token
     * @param token jwtToken
     * @return true if expiration date is over, false if not
     */
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = this.getExpirationDateFromToken(token);
//        return expiration.before(this.generateCurrentDate());
//    }

    /**
     * this method is intended for checking time of creation jwtToken and time when user change his password
     * if the time of the first is less than the time of the second we need to change jwtToken
     *
     * @param created           created time of jwtToken
     * @param lastPasswordReset password reset time
     * @return true if password reset date was change after creation time of jwtToken, true if not
     */
    public Boolean isCreatedBeforeLastPasswordReset(Date created, LocalDateTime lastPasswordReset) {
        LocalDateTime createdLocalDateTime = created.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return (lastPasswordReset != null && createdLocalDateTime.isBefore(lastPasswordReset));
    }


    /**
     * generate token from claims, expirationDate, algorithm and secret
     *
     * @param claims claims of user
     * @return jwt token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtTokenConfig.getSecret())
                .compact();
    }


    /**
     * add claims
     *
     * @param userDetails basic information from userProvider which is implemented in  {@link q3df.mil.security.service.UserServiceProvider}
     * @return jwtToken
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        UserHelperClaims userIdAndPCByLogin = findUserIdAndPCByLogin(userDetails);
        claims.put(USER_ID, userIdAndPCByLogin.getId());
        claims.put(P_CHANGE, userIdAndPCByLogin.getPChange());
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(CREATE_VALUE, generateCurrentDate());
        claims.put(ROLES, getEncryptedRoles(userDetails));
        return generateToken(claims);
    }


    /**
     * find user id and pChange by user details
     *
     * @param userDetails basic information from userProvider which is implemented in  {@link q3df.mil.security.service.UserServiceProvider}
     * @return UserHelperClaims with user id and pChange
     * @throws UserNotFoundException if user is not found
     */
    private UserHelperClaims findUserIdAndPCByLogin(UserDetails userDetails) {
        User user = userRepository
                .findByLogin(userDetails.getUsername())
                .orElseThrow(() ->
                        new UserNotFoundException("User with login " + userDetails.getUsername() + " not found!"));
        return new UserHelperClaims(user.getId(), user.getPChange());
    }

    /**
     * get List of Roles without prefix ROLE_
     *
     * @param userDetails basic information from userProvider which is implemented in  {@link q3df.mil.security.service.UserServiceProvider}
     * @return the List of user roles without prefix ROLE_
     */
    private List<String> getEncryptedRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().
                stream()
                .map(GrantedAuthority::getAuthority)
                .map(s -> s.replace("ROLE_", ""))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }


    /**
     *  ->  IT SEEMS LIKE THIS METHOD IS NOT NEEDED <-
     * @param token jwtToken
     * @param lastPasswordReset date of password reset
     * @return true if password reset date was change after creation time of jwtToken or  the token has expired
     */
//    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = this.getCreatedDateFromToken(token);
//        return this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
//                || this.isTokenExpired(token);
//    }

//    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
//        final Date created = this.getCreatedDateFromToken(token);
//        return !(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
//                && !(this.isTokenExpired(token));
//    }

    /**
     * refresh token
     *
     * @param claims claims of Expired jwtToken
     * @return new jwtToken
     */
    public String refreshToken(Claims claims) {
        String refreshedToken;
        try {
            claims.put(CREATE_VALUE, this.generateCurrentDate());
            refreshedToken = this.generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;

    }


    /**
     * ->  IT SEEMS LIKE THIS METHOD IS NOT NEEDED <-
     *
     * @param token       jwtToken
     * @param userDetails basic information from userProvider which is implemented in  {@link q3df.mil.security.service.UserServiceProvider}
     * @return false if login from jwtToken !equals login from userDetails
     */
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return username.equals(userDetails.getUsername());
//    }


    @Data
    @AllArgsConstructor
    class UserHelperClaims {
        private Long id;
        private LocalDateTime pChange;
    }


}

