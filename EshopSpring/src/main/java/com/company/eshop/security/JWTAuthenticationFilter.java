package com.company.eshop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.company.eshop.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class JWTAuthenticationFilter {
    private String SECRET;
    private Long lifetime;

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, Environment environment) {
        this.authenticationManager = authenticationManager;
        this.SECRET = environment.getProperty("eshop.jwt.secret");
        this.lifetime = Long.valueOf(Objects.requireNonNull(environment.getProperty("eshop.jwt.lifetime")));
    }


    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            User userToAuthenticate = new ObjectMapper()
                    .readValue( request.getInputStream(), User.class);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userToAuthenticate.getUsername(), userToAuthenticate.getPassword())
            );
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void successfulAuthentication(HttpServletRequest request,
                                         HttpServletResponse response,
                                         FilterChain chain,
                                         Authentication authentication) {
        org.springframework.security.core.userdetails.User user = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal());
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + lifetime))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC256( SECRET.getBytes() ));
        response.addHeader("Authorization", "Bearer " + token);
    }

}