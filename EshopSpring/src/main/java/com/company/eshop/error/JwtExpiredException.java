package com.company.eshop.error;

import org.springframework.security.authentication.InsufficientAuthenticationException;

public class JwtExpiredException extends InsufficientAuthenticationException {
    public JwtExpiredException(String msg) {
        super(msg);
    }
}
