package com.hcc.controllers;

import com.hcc.dto.AuthCredentialsRequest;
import com.hcc.entities.User;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for authentication-related endpoints.
 *
 * Provides endpoints to:
 * Authenticate a user using username/password and issue a JWT
 * Validate a JWT token
 *
 * Base path: {@code /api/auth}
 *
 * Authentication is performed via Spring Security's {@link AuthenticationManager}.
 * On successful login, a JWT is generated using {@link JwtUtil} and returned in the
 * {@code Authorization} response header.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // POST /api/auth/login
    @CrossOrigin("*")
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
        try {

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                    request.getPassword()));
            User user = (User) auth.getPrincipal();
            user.setPassword(null);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtUtil.generateToken(user)).body(user.getUsername());
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // GET /api/auth/validate?token=
    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token, @AuthenticationPrincipal User user) {
        try {
            if (user != null) {
                boolean isTokenValid = jwtUtil.validateToken(token, user);
                return ResponseEntity.ok(isTokenValid);
            } else {
                return ResponseEntity.ok(false);
            }
        } catch (Exception ex) {
            return ResponseEntity.ok(false);
        }
    }
}
