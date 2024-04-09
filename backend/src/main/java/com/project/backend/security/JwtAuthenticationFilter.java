package com.project.backend.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
    // Map of endpoints to user roles for authorization
    private static final Map<String, String> authorities = Map.ofEntries(Map.entry("/teacher", UserRole.TEACHER.name()),
                                                                         Map.entry("/student", UserRole.STUDENT.name()));
    // List of request URIs to exclude from JWT authentication
    private static final List<String> excludePattern = Arrays.asList("/", "/login", "/signup");

    // Override the doFilterInternal method to apply JWT authentication logic
    @SuppressWarnings("unused")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Skip JWT authentication for specified URIs
        if (excludePattern.contains(request.getRequestURI()) /*|| truefor testing only, remove on production*/) {
            filterChain.doFilter(request, response);
            return;
        }
        // Check if the request URI exists
        if (!authorities.containsKey(request.getRequestURI())) {
            exceptionLog.log(new IOException(this.getClass().getName()));
            response.sendError(HttpStatus.NOT_FOUND.value());
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
            String id = jwtUtils.getId(claims);
            String password = jwtUtils.getPassword(claims);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                // Check if the current authentication matches the JWT claims
                if (authentication.getName().equals(id) &&
                    BackendSecurityConfiguration.encoder.matches(password, authentication.getCredentials().toString()) &&
                    authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).anyMatch(name -> name.equals(authorities.get(request.getRequestURI())))) {
                    filterChain.doFilter(request, response);
                } else {
                    exceptionLog.log(new IOException(this.getClass().getName()));
                    response.sendError(HttpStatus.UNAUTHORIZED.value());
                }
            } else {
                // Authenticate using JWT claims if no authentication is present
                Authentication jwtAuthentication = manager.authenticate(new UsernamePasswordAuthenticationToken(jwtUtils.getEmail(claims), password));
                if (jwtAuthentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
                    if (jwtAuthentication.getAuthorities().stream().map(auth -> auth.getAuthority()).anyMatch(name -> name.equals(authorities.get(request.getRequestURI())))) {
                        filterChain.doFilter(request, response);
                    } else {
                        exceptionLog.log(new IOException(this.getClass().getName()));
                        response.sendError(HttpStatus.UNAUTHORIZED.value());
                    }
                } else {
                    exceptionLog.log(new IOException(this.getClass().getName()));
                    response.sendError(HttpStatus.UNAUTHORIZED.value());
                }
            }
        } else {
            // Log and reject the request if the JWT is invalid
            exceptionLog.log(new IOException(this.getClass().getName() + "valid"));
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }
}