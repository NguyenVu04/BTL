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
    @Autowired
    private ExceptionLog exceptionLog;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager manager;
    private static final Map<String, String> authorities = Map.ofEntries(Map.entry("/teacher", UserRole.TEACHER.name()),
                                                                         Map.entry("/student", UserRole.STUDENT.name()));
    private static final List<String> excludePattern = Arrays.asList("/", "/login", "/signup");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (excludePattern.contains(request.getRequestURI()) || true) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!authorities.containsKey(request.getRequestURI())) {
            exceptionLog.log(new IOException(this.getClass().getName()));
            response.sendError(HttpStatus.NOT_FOUND.value());
            return;
        }
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            exceptionLog.log(new IOException(this.getClass().getName()));
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        token = token.split(" ")[1].trim();
        Claims claims = jwtUtils.decodeToken(token);
        if (jwtUtils.isValid(claims)) {
            String id = jwtUtils.getId(claims);
            String password = jwtUtils.getPassword(claims);
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
                if (authentication.getName()
                        .equals(id) &&
                        BackendSecurityConfiguration.encoder
                                .matches(password, authentication.getCredentials()
                                        .toString())
                        &&
                        authentication.getAuthorities()
                                .stream()
                                .map(auth -> auth.getAuthority())
                                .anyMatch(name -> name.equals(authorities.get(
                                        request.getRequestURI())))) {
                    filterChain.doFilter(request, response);
                } else {
                    exceptionLog.log(new IOException(this.getClass().getName()));
                    response.sendError(HttpStatus.UNAUTHORIZED.value());
                }
            } else {
                Authentication jwtAuthentication = manager
                        .authenticate(new UsernamePasswordAuthenticationToken(jwtUtils.getEmail(claims), password));
                if (jwtAuthentication.isAuthenticated()) {
                    SecurityContextHolder.getContext()
                                         .setAuthentication(jwtAuthentication);
                    if (jwtAuthentication.getAuthorities()
                                         .stream()
                                         .map(auth -> auth.getAuthority())
                                         .anyMatch(name -> name.equals(authorities.get(
                                                           request.getRequestURI())))) {
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
            exceptionLog.log(new IOException(this.getClass().getName() + "valid"));
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }
    }

}
