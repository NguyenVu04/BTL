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
    public static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private BackendDetailsService service;
    @Autowired
    private ExceptionLog exceptionLog;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http//.csrf(crsf -> crsf.requireCsrfProtectionMatcher(req -> req.getRequestURI().equals("/home")))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }
    @Bean 
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(new BackendAuthenticationProvider(BackendSecurityConfiguration.encoder, service, exceptionLog));
        return builder.build();
    }
}
