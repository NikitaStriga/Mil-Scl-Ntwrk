package q3df.mil.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import q3df.mil.security.util.TokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static q3df.mil.security.util.JwtConstants.CREATE_VALUE;
import static q3df.mil.security.util.JwtConstants.P_CHANGE;
import static q3df.mil.security.util.JwtConstants.ROLES;
import static q3df.mil.security.util.JwtConstants.USER_ID;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;


    @Autowired
    public JwtTokenFilter(TokenUtils tokenUtils, @Qualifier("userServiceProvider") UserDetailsService userDetailsService) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {

        //receive token
        String token = request.getHeader("X-Auth-Token");

        //check for toke exist and for valid
        if(token!=null&&tokenUtils.validateToken(token)) {
            //setAuthentication and add attribute to request
            setAuthIntoContext(token,request);
        }

        //if token expired
        }catch (ExpiredJwtException expEx){

            //get claims of token
            Claims claims = expEx.getClaims();

            //get URL
            String requestUrl = request.getRequestURL().toString();

            //check if password was changed after creation token and whether the URL contains refresh token
            if(!tokenUtils.
                        isCreatedBeforeLastPasswordReset(
                                claims.get(CREATE_VALUE, Date.class), claims.get(P_CHANGE, LocalDateTime.class))&&
                        requestUrl.contains("refreshtoken")){

                    //by this method we set into request attribute claims with info about claims
                    allowForRefreshToken(expEx,request);
            }else {
                    request.setAttribute("exception", expEx);
            }

        //throw BadCredentialsException if parsing of Jwt Token throw UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException
        //or throw UsernameNotFoundException if UserDetailsService cant find username by use loadUserByUsername()
        }catch (BadCredentialsException | UsernameNotFoundException ex) {
            request.setAttribute("exception", ex);
        }

        //do filter
        chain.doFilter(request, response);
    }



    //setAuthentication
    private void setAuthIntoContext(String token,HttpServletRequest request){

        //get Claims
        Claims claimsFromToken = tokenUtils.getClaimsFromToken(token);

        //get userName from token
        String usernameFromToken = claimsFromToken.getSubject();



        //get userId and then pass it to request attribute
        {
            Long userID = claimsFromToken.get(USER_ID,Long.class);
            request.setAttribute("id", userID);
        }

        //check the token for the presence of the admin role, and if it exists,
        // then set the permission = true attribute in the request
        {
            if(claimsFromToken.get(ROLES,List.class).contains("ADMIN")){
                request.setAttribute("permission","true");
            }
        }

        //get UserDetails by login (username)
        //can throw new UsernameNotFoundException("Cant found user with login " + login);
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);


        // create implementation of Authenticate
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //by this security context will determine is the passed authentication object valid
        SecurityContextHolder.getContext().setAuthentication(auth);
    }


    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }
}
