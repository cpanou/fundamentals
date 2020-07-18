package com.company.eshop.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.company.eshop.error.ApplicationError;
import com.company.eshop.error.JwtExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private String SECRET;

    public JWTAuthorizationFilter(AuthenticationManager authManager, Environment environment) {
        super(authManager);
        this.SECRET = environment.getProperty("eshop.jwt.secret");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.contains("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace("Bearer ", "");
        String username = "";
        try {
            username = JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (RuntimeException e) {
            if ( e instanceof TokenExpiredException) {
                sendExpiredResponse(response);
                return;
            }
            throw new JWTVerificationException(e.getLocalizedMessage());
        }
        Authentication usernameAuthToken = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(usernameAuthToken);
        filterChain.doFilter(request, response);
    }

    private void sendExpiredResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OutputStream out = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(out, new ApplicationError("Token Expired", 102));
        out.flush();
    }


}