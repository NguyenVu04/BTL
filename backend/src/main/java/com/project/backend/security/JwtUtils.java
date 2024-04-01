package com.project.backend.security;

import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.exceptionhandler.ExceptionLog;
import com.project.backend.repository.FirestoreRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;

@Service
public class JwtUtils {
    @Autowired
    private FirestoreRepository repository;
    @Autowired
    private ExceptionLog exceptionLog;
    private final SecretKey encodeKey = Jwts.KEY.A256KW.key().build();
    private final SecretKey decodeKey = Jwts.KEY.A256GCMKW.key().build();
    private final AeadAlgorithm algorithm = Jwts.ENC.A256GCM;
    public Claims decodeToken(String token) {
        try {
            return Jwts.parser()
                   .decryptWith(decodeKey)
                   .build()
                   .parseEncryptedClaims(token)
                   .getPayload();
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
            return null;
        }                   
    }
    public String encodeObject(String id, Map<String, Object> object) {
        Date now = new Date();
        Date expDate = new Date(now.getTime() + 20000);
        try {
            return Jwts.builder()
                       .claims(object)
                       .id(id)
                       .encryptWith(encodeKey, algorithm)
                       .expiration(expDate)
                       .compact();
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }
    }
    @Nullable
    public String encodeObjectById(String id) {
        DocumentSnapshot snapshot = repository.getDocumentById(AuthenticationDetails.class, id);
        if (snapshot == null) {
            return null;
        }
        Map<String, Object> object = snapshot.getData();
        if (object != null) {
            object.remove("id");
        } else {
            return null;
        }
        Date now = new Date();
        Date expDate = new Date(now.getTime() + 20000);
        return Jwts.builder()
                   .expiration(expDate)
                   .id(id)
                   .claims(object)
                   .encryptWith(encodeKey, algorithm)
                   .compact();
    }
    public boolean isValid(Claims claims) {
        try {
            Date now = new Date();
            if (claims.containsKey("id") && 
                claims.containsKey("password") && 
                claims.getExpiration().before(now)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
            return false;
        }
    }
    @Nullable
    public String getId(Claims claims) {
        return claims.get("id", String.class);
    }
    @Nullable
    public String getPassword(Claims claims) {
        return claims.get("password", String.class);
    }
}
