package com.company.eshop.security;

import com.company.eshop.error.ApplicationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import com.company.eshop.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment environment;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated()
                .and()
                //Spring Security Exception Handling
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    ApplicationError error = new ApplicationError("Un Authorized",1 );
                    
                    response.setStatus(401);
                    response.setContentType("application/json");

                    OutputStream out = response.getOutputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(out, error);
                    out.flush();
                })
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), environment))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), environment))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return urlBasedCorsConfigurationSource;
    }

}