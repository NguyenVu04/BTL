package com.project.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.FirestoreRepository;
import com.project.backend.security.AuthenticationDetails;
import com.project.backend.security.JwtUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private ExceptionLog exceptionLog;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private FirestoreRepository repository;
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> login(@RequestParam(name = "email", required = true) String email,  
                                        @RequestParam(name = "password", required = true) String password) {
        try {
            Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                List<DocumentSnapshot> snapshots = repository.getDocumentsByField(AuthenticationDetails.class,
                                                                 "userId",
                                                                            authentication.getName(), 
                                                                            1); 
                if (snapshots != null && !snapshots.isEmpty()) {
                    Map<String, Object> map = snapshots.get(0)
                                                       .getData();
                    if (map != null) {
                        String token = jwtUtils.encodeObject(map);
                        map.remove("password");
                        Map<String, List<String>> header = new HashMap<String, List<String>>();
                        header.put("Authorization", Arrays.asList(token));
                        return new ResponseEntity<Map<String,Object>>(map, 
                                                                    new MultiValueMapAdapter<String, String>(header), 
                                                                    HttpStatus.OK);
                    } else {
                        exceptionLog.log(new UsernameNotFoundException(this.getClass().getName()));
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                } else {
                    exceptionLog.log(new UsernameNotFoundException(this.getClass().getName()));
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                exceptionLog.log(new UsernameNotFoundException(this.getClass().getName()));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            exceptionLog.log(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }        
    }
    
}
