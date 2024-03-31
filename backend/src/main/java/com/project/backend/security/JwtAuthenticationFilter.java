package com.project.backend.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final List<String> excludePattern = Arrays.asList("/", "/login", "/signup");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);  
        /*if (excludePattern.contains(request.getHttpServletMapping().getPattern())) {
            filterChain.doFilter(request, response);
            return;
        }
        //TODO:Add CRSF and CORS on production
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null || !token.startsWith("Bearer ")) {
            IOException e = new IOException(this.getClass().getName());
            exceptionLog.log(e);
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        token = token.split(" ")[1].trim();
        Claims claims = jwtUtils.decodeToken(token);
        if (claims != null) {
            String id = jwtUtils.getId(claims);
            String password = jwtUtils.getPassword(claims);
            Authentication authentication = SecurityContextHolder.getContext()
                                                                 .getAuthentication();
            if (authentication != null &&
                authentication.getName()
                              .equals(id) && 
                authentication.getCredentials()
                              .toString()
                              .equals(password)) {
                filterChain.doFilter(request, response);
            } else {
                IOException e = new IOException(this.getClass().getName());
                exceptionLog.log(e);
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            IOException e = new IOException(this.getClass().getName());
            exceptionLog.log(e);
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }*/
    }

}
