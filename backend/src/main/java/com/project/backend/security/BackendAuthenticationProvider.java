package com.project.backend.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.backend.exceptionhandler.ExceptionLog;

@Service
public class BackendAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private BackendDetailsService service;
    @Autowired
    private ExceptionLog exceptionLog;
    public final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthenticationDetails details = service.loadUserByEmail(email);
        String userPassword = details.getPassword();

        //if (encoder.matches(password, userPassword)) {
        if (password.equals(userPassword)) {
            List<SimpleGrantedAuthority> list = details.getRole()
                                                       .stream()
                                                       .map(role -> new SimpleGrantedAuthority(role))
                                                       .toList();
            return UsernamePasswordAuthenticationToken.authenticated(details.getId(), password, list);
        } else {
            AuthenticationException e = new UsernameNotFoundException(userPassword + "!=" + password);
            exceptionLog.log(e);
            throw e;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
