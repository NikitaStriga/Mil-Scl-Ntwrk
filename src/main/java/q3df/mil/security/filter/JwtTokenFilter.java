package q3df.mil.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import java.sql.Date;



@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";

    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtTokenFilter(TokenUtils tokenUtils, @Qualifier("userServiceProvider") UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {

        //receive token
        String token = request.getHeader("X-Auth-Token");

        //check for toke exist and for valid
        if(token!=null&&tokenUtils.validateToken(token)) {
            //setAuthentication
            setAuthIntoContext(token);
        }

        //if token expired
        }catch (ExpiredJwtException expEx){

            //get claims of token
            Claims claims = expEx.getClaims();

            //get URL
            String requestUrl = request.getRequestURL().toString();

            //check if password was changed after creation token and whether the URL contains refreshtoken
            if(!tokenUtils.
                        isCreatedBeforeLastPasswordReset(
                                (Date) claims.get("created"), Date.valueOf("1000-01-01"))&&
                        requestUrl.contains("refreshtoken")){

                    //by this method we set into request attribute claims with info about claims
                    allowForRefreshToken(expEx,request);
            }else {
                    request.setAttribute("exception", expEx);
            }

        //throw BadCredentialsException if parsing of Jwt Token throw UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException
        //or throw UsernameNotFoundException if UserDetailsService cant find username by use loadUserByUsername()
        }catch (BadCredentialsException |UsernameNotFoundException ex) {
            request.setAttribute("exception", ex);
        }

        //do filter
        chain.doFilter(request, response);
    }



    //setAuthentication
    private void setAuthIntoContext(String token){
        //get userName from token
        String usernameFromToken = tokenUtils.getUsernameFromToken(token);

        //get UserDetails by login (username)
        //can throw new UsernameNotFoundException("Cant found user with login " + login);
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);

        //create implementation of Authenticate
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
