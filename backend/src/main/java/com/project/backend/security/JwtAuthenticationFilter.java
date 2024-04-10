package com.project.backend.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.backend.exceptionhandler.ExceptionLog;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Autowired instance of ExceptionLog for logging exceptions
    @Autowired
    private ExceptionLog exceptionLog;
    // Autowired instance of JwtUtils for JWT operations
    @Autowired
    private JwtUtils jwtUtils;
    // Autowired instance of AuthenticationManager for authentication
    @Autowired
    private AuthenticationManager manager;
    // List of request URIs to exclude from JWT authentication
    private static final List<String> excludedPath = Arrays.asList("/", "/login", "/signup");
    // List of request URIs required JWT authentication
    private static final List<String> authenticatedPath= Arrays.asList("/logout");
    // Override the doFilterInternal method to apply JWT authentication logic
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Skip JWT authentication for specified URIs
        if (excludedPath.contains(request.getRequestURI()) || true/*for testing only, remove on production */) {
            filterChain.doFilter(request, response);
            return;
        }
        // Extract the JWT token from the request header
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            exceptionLog.log(new IOException(this.getClass().getName()));
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        // Decode the JWT token and validate it
        token = token.split(" ")[1].trim();
        Claims claims = jwtUtils.decodeToken(token);
        if (jwtUtils.isValid(claims)) {
            // Perform authentication based on the JWT claims
            String email = jwtUtils.getEmail(claims);
            String password = jwtUtils.getPassword(claims);
            Authentication jwtAuthentication = manager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            if (!jwtAuthentication.isAuthenticated()) {
                exceptionLog.log(new IOException(this.getClass().getName()));
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            SecurityContextHolder.getContext()
                                 .setAuthentication(jwtAuthentication);
            if (authenticatedPath.contains(request.getRequestURI()) ||
                jwtAuthentication.getAuthorities()
                                 .stream()
                                 .map(auth -> auth.getAuthority())
                                 .anyMatch(name -> name.toLowerCase()
                                                       .equals(request.getRequestURI()
                                                       .split("/")[1]))) {
                filterChain.doFilter(request, response);
                return;
            } else {
                exceptionLog.log(new IOException(this.getClass().getName()));
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            // Log and reject the request if the JWT is invalid
            exceptionLog.log(new IOException(this.getClass().getName()));
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}