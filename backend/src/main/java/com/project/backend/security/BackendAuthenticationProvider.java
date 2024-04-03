package com.project.backend.security;


import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.project.backend.exceptionhandler.ExceptionLog;

public class BackendAuthenticationProvider implements AuthenticationProvider {
    private final BackendDetailsService service;
    private final ExceptionLog exceptionLog;
    public final PasswordEncoder encoder;
    public BackendAuthenticationProvider(PasswordEncoder encoder, 
                                         BackendDetailsService service, 
                                         ExceptionLog exceptionLog){
        this.encoder = encoder;
        this.exceptionLog = exceptionLog;
        this.service = service;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        AuthenticationDetails details = service.loadUserByEmail(email);
        if (details == null) {
            exceptionLog.log(new UsernameNotFoundException(this.getClass().getName() + ": " + authentication.toString()));
            authentication.setAuthenticated(false);
            return authentication;
        }
        String password = authentication.getCredentials()
                                        .toString();
        String userPassword = details.getPassword();

        if (encoder.matches(password, userPassword)) {
            List<SimpleGrantedAuthority> list = List.of(new SimpleGrantedAuthority(details.getRole()));
            return UsernamePasswordAuthenticationToken.authenticated(details.getUserId(), password, list);
        } else {
            exceptionLog.log(new UsernameNotFoundException(this.getClass().getName() + ": " + authentication.toString()));
            authentication.setAuthenticated(false);
            return authentication;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
