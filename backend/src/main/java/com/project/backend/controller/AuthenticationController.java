package com.project.backend.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.Timestamp;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.FirestoreRepository;
import com.project.backend.security.AuthenticationDetails;
import com.project.backend.security.JwtUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
@RestController
public class AuthenticationController {
    /**
     * The AuthenticationManager used to authenticate users.
     */
    @Autowired
    private AuthenticationManager manager;
    /**
     * An instance of the ExceptionLog class used to log exceptions that occur
     * during authentication.
     */
    @Autowired
    private ExceptionLog exceptionLog;
    /**
     * An instance of the JwtUtils class used to generate and validate JSON Web
     * Tokens (JWT) for authentication.
     */
    @Autowired
    private JwtUtils jwtUtils;
    /**
     * An instance of the FirestoreRepository class used to interact with the
     * Firestore
     * database for authentication-related operations.
     */
    @Autowired
    private FirestoreRepository repository;

    /**
     * Handles the login process for the application.
     *
     * @param email    The email address of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return A ResponseEntity containing a map with the user's ID, role, and an
     *         authorization token if the login is successful. Otherwise, returns an
     *         unauthorized response.
     */
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam(name = "email", required = true) String email,
            @RequestParam(name = "password", required = true) String password,
            @RequestParam (name = "role", required = true) String role) {
        try {
            Authentication authentication = manager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password, List.of(new SimpleGrantedAuthority(role))));
            if (authentication.isAuthenticated()) {
                Map<String, Object> map = new HashMap<>();
                map.put("password", password);
                map.put("id", authentication.getName());
                map.put("role", authentication.getAuthorities()
                        .iterator()
                        .next()
                        .toString());
                String token = jwtUtils.encodeObject(map);
                map.put("authorization", token);
                map.remove("password");
                return ResponseEntity.ok().body(map);
            } else {
                exceptionLog.log(new UsernameNotFoundException(this.getClass().getName()));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Handles the logout process for the application.
     *
     * @return A ResponseEntity containing a successful response if the logout is
     *         successful. Otherwise, returns an unauthorized response.
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            exceptionLog.log(new AuthorizationServiceException(this.getClass().getName()));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = authentication.getName();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lastLogout", Timestamp.now());
        boolean result = repository.updateDocumentById(AuthenticationDetails.class, userId, map);
        if (!result) {
            exceptionLog.log(new AuthorizationServiceException(this.getClass().getName()));
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
