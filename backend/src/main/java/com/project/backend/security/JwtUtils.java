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
    // Autowired instance of ExceptionLog for logging exceptions
    @Autowired
    private ExceptionLog exceptionLog;
    // SecretKey used for JWT encryption and decryption
    private SecretKey key;
    // File name for storing the secret key
    private static final String keyFile = "SecretKey";

    /**
     * Initializes the secret key used for JWT encryption and decryption.
     * If a file named "SecretKey" exists, the key is loaded from the file.
     * Otherwise, a new secret key is generated and stored in the "SecretKey" file.
     * Any exceptions that occur during the initialization process are logged using
     * the `ExceptionLog` service.
     */
    public JwtUtils() {
        try (InputStream input = new FileInputStream(keyFile)) {
            byte[] bytes = input.readAllBytes();
            key = new SecretKeySpec(bytes, "AES");
        } catch (Exception e) {
            exceptionLog.log(e);
            // Generate a new secret key if the file is not found
            key = Jwts.KEY.A256GCMKW.key().build();
            try (PrintStream print = new PrintStream(new FileOutputStream("key", false))) {
                print.write(key.getEncoded());
            } catch (Exception __e) {
                exceptionLog.log(__e);
            }
        }
    }
    
    /**
     * Decodes the provided JWT token and returns the claims payload.
     * 
     * @param token The JWT token to decode.
     * @return The claims payload of the decoded token, or null if an exception
     *         occurs during decoding.
     */
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

    /**
     * Encodes a map of user data into a JWT token.
     *
     * @param map A map containing the user's email, password, and userId.
     * @return The encoded JWT token, or null if an exception occurs during
     *         encoding.
     */
    public String encodeObject(Map<String, Object> map) {
        if (!map.containsKey("userId"))
            return null;
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

    /**
     * Checks if the provided JWT claims are valid. The claims must contain the
     * 'email' and 'password' keys, and the token must not be expired.
     *
     * @param claims The JWT claims to validate.
     * @return True if the claims are valid, false otherwise.
     */
    public boolean isValid(Claims claims) {
        try {
            Date now = new Date();
            if (claims != null &&
                    claims.containsKey("email") &&
                    claims.containsKey("password") &&
                    claims.getExpiration().after(now)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            exceptionLog.log(e, this.getClass().getName());
            return false;
        }
    }

    // Retrieves the user ID from the claims
    @Nullable
    public String getId(Claims claims) {
        return claims.getId();
    }

    // Retrieves the password from the claims
    @Nullable
    public String getPassword(Claims claims) {
        return claims.get("password", String.class);
    }

    // Retrieves the email from the claims
    @Nullable
    public String getEmail(Claims claims) {
        return claims.get("email", String.class);
    }
}
