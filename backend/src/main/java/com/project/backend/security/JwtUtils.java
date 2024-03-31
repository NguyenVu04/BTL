package com.project.backend.security;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentSnapshot;
import com.project.backend.repository.FirestoreRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;

@Service
public class JwtUtils {
    @Autowired
    private FirestoreRepository repository;
    private final SecretKey encodeKey = Jwts.KEY.A256KW.key().build();
    private final SecretKey decodeKey = Jwts.KEY.A256GCMKW.key().build();
    private final SecretKey signKey = Jwts.KEY.A128GCMKW.key().build();
    private final AeadAlgorithm algorithm = Jwts.ENC.A256GCM;
    public Claims decodeToken(String token) {
        return Jwts.parser()
                   .decryptWith(decodeKey)
                   .build()
                   .parseEncryptedClaims(token)
                   .getPayload();
                   
    }
    public String encodeObject(String id) {
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
                   .signWith(signKey)
                   .expiration(expDate)
                   .issuedAt(now)
                   .id(id)
                   .claims(object)
                   .encryptWith(encodeKey, algorithm)
                   .compact();
    }
    public boolean isValid(String token) {
        Date now = new Date();
        Claims claims = decodeToken(token);
        if (claims.containsKey("id") && 
            claims.containsKey("password") && 
            claims.getExpiration().before(now)) {
            return true;
        }
        return false;
    }
    public String getId(String token) {
        Claims claims = decodeToken(token);
        return claims.get("id", String.class);
    }
    public String getPassword(String token) {
        Claims claims = decodeToken(token);
        return claims.get("password", String.class);
    }
}
