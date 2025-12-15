package com.hcc.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Component that provides a centralized {@link PasswordEncoder} implementation.
 *
 * This class wraps Spring Security's {@link BCryptPasswordEncoder} and exposes
 * it as a reusable bean. Centralizing the password encoder allows the encoding
 * strategy to be easily changed or extended in the future without modifying
 * authentication logic across the application.
 */
@Component
public class CustomPasswordEncoder {
    private PasswordEncoder passwordEncoder;

    public CustomPasswordEncoder(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoder getPasswordEncoder(){
        return passwordEncoder;
    }
}
