package com.hcc.filters;

import com.hcc.repositories.UserRepository;
import com.hcc.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * This filter checks the {@code Authorization} header for a bearer token of the form:
 * {@code "Bearer <jwt>"}.
 * If a token is present, it:
 * Extracts the JWT from the header
 * Reads the username from the token
 * Loads the corresponding {@link org.springframework.security.core.userdetails.UserDetails}
 *      from the database via {@link UserRepository}
 * Validates the token using {@link JwtUtil}
 * If valid, creates an {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 *      and stores it in the {@link org.springframework.security.core.context.SecurityContextHolder}
 *
 * If the header is missing, does not start with {@code "Bearer "}, or the token is invalid,
 * the filter does not authenticate the request and simply continues the filter chain.
 * Protected endpoints will then be rejected by Spring Security with a 401 response.
 */
@Component
public class jwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get Authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        // Get Jwt Token
        final String token = header.split(" ")[1].trim();

        // Get user identity
        UserDetails userDetails = userRepo.findByUsername(jwtUtil.getUsernameFromToken(token)).orElse(null);

        if (!jwtUtil.validateToken(token, userDetails)) {
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);

    }
}