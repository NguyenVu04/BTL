package com.project.backend.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.backend.exceptionhandler.ExceptionLog;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtUtils {
    @Autowired
    private ExceptionLog exceptionLog;
    private SecretKey key;
    private static final String keyFile = "SecretKey";
    public JwtUtils() {
        try (InputStream input = new FileInputStream(keyFile)) {
            byte[] bytes = input.readAllBytes();
            key = new SecretKeySpec(bytes, "AES");
        } catch (Exception e) {
            exceptionLog.log(e);
            key = Jwts.KEY.A256GCMKW.key().build();
            try (PrintStream print = new PrintStream(new FileOutputStream("key", false))) {
                print.write(key.getEncoded());
            } catch (Exception __e) {
                exceptionLog.log(__e);
            }
        }
    }
    public Claims decodeToken(String token) {
        try {
            return Jwts.parser()
                       .decryptWith(key)
                       .build()
                       .parseEncryptedClaims(token)
                       .getPayload();
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }                   
    }
    public String encodeObject(Map<String, Object> map) {
        if (!map.containsKey("userId")) return null;
        String id = map.get("userId").toString();
        Map<String, Object> object = new HashMap<String, Object>();
        object.put("email", map.get("email"));
        object.put("password", map.get("password"));
        Date now = new Date();
        Date expDate = new Date(now.getTime() + 3600000);
        try {
            return Jwts.builder()
                       .claims(object)
                       .id(id)
                       .encryptWith(key, Jwts.ENC.A256GCM)
                       .expiration(expDate)
                       .compact();
        } catch (Exception e) {
            exceptionLog.log(e);
            return null;
        }
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
    @Nullable
    public String getUsername(Claims claims) {
        return claims.get("username", String.class);
    }
    @Nullable
    public String getEmail(Claims claims) {
        return claims.get("email", String.class);
    }
}
