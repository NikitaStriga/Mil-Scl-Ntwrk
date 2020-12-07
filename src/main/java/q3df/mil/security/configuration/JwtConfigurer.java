//package q3df.mil.security.configuration;
//
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import q3df.mil.security.filter.JwtTokenFilter;
//import q3df.mil.security.util.TokenUtils;
//
//public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private TokenUtils tokenUtils;
//
//    public JwtConfigurer(TokenUtils tokenUtils) {
//        this.tokenUtils = tokenUtils;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        JwtTokenFilter customFilter = new JwtTokenFilter();
//        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//
//}
