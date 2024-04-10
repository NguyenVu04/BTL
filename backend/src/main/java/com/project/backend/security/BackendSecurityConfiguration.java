package com.project.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.backend.exceptionhandler.ExceptionLog;

@Configuration
@EnableWebSecurity
public class BackendSecurityConfiguration {
    // Password encoder for hashing passwords
    public static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    // Autowired instance of BackendDetailsService for user details retrieval
    @Autowired
    private BackendDetailsService service;

    // Autowired instance of ExceptionLog for logging exceptions
    @Autowired
    private ExceptionLog exceptionLog;

    // Bean for configuring the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection for simplicity (not recommended for production)
        http.csrf(csrf -> csrf.disable())
            .logout(out -> out.disable())
            //.csrf(csrf -> csrf.requireCsrfProtectionMatcher(req -> req.getRequestURI().equals("/user")))
            // Configure session management to be stateless
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    // Bean for configuring the AuthenticationManager with a custom authentication provider
    @Bean 
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        // Use a custom authentication provider with BCryptPasswordEncoder, BackendDetailsService, and ExceptionLog
        builder.authenticationProvider(new BackendAuthenticationProvider(BackendSecurityConfiguration.encoder, service, exceptionLog));
        return builder.build();
    }
}