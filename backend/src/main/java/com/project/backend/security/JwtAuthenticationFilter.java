package com.project.backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.backend.exceptionhandler.ExceptionLog;

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
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            IOException e = new IOException(this.getClass().getName());
            exceptionLog.log(e);
            filterChain.doFilter(request, response);
            return;
        }
        token = token.split(" ")[1].trim();
        if (jwtUtils.isValid(token)) {
            String id = jwtUtils.getId(token);
            String password = jwtUtils.getPassword(token);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getName()
                              .equals(id) && 
                authentication.getCredentials()
                              .toString()
                              .equals(password)) {
            } else {
                IOException e = new IOException(this.getClass().getName());
                exceptionLog.log(e);
            }
        } else {
            IOException e = new IOException(this.getClass().getName());
            exceptionLog.log(e);
        }
        filterChain.doFilter(request, response);
    }

}
