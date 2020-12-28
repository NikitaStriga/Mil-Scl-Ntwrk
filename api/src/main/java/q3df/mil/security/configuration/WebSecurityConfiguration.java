package q3df.mil.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import q3df.mil.security.exceptionhandling.authentication.JwtAuthenticationEntryPoint;
import q3df.mil.security.filter.JwtTokenFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public WebSecurityConfiguration(@Qualifier("userServiceProvider") UserDetailsService userProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtTokenFilter jwtTokenFilter) {
        this.userProvider = userProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder passwordEncoder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userProvider)
                .passwordEncoder(passwordEncoder);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                //disable csrf support
                .csrf()
                .disable()

                //session is not created and not used by Spring Security
                //every request needs to be re-authenticated
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //enable antMatchers (allows restricting access by url patterns which are below)
                .and().authorizeRequests()

                //matchers
                .antMatchers(HttpMethod.GET, "/swagger-ui.html#").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("/authentication/**").permitAll()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/refresh/**").permitAll()
                .antMatchers("/recovery/**").permitAll()
                .antMatchers("/users/**").permitAll() //hasAnyRole
                .antMatchers("/roles/**").permitAll() //for admin
                .antMatchers("/admin/**").permitAll() //for admin

                //all requests except matchers above must be authenticated
                .anyRequest().authenticated()

                //registering a custom security exception handler
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

                //registering a custom filter
                .and().addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }


    @Override
    public void configure(WebSecurity web) throws Exception {

        web
                //ignore swagger files
                .ignoring()
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui/**",
                        "/swagger-resources/**",
                        "/configuration/security/**",
                        "/swagger-ui.html",
                        "/webjars/**");
    }

}
