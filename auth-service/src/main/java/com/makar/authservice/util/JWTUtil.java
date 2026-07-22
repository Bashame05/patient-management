package com.makar.authservice.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTUtil {
    private final Key secretKey;
    public JWTUtil(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ Duration.ofHours(10).toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) {
        try{
            Jwts.parser().verifyWith((SecretKey)secretKey)
                    .build()
                    .parseSignedClaims(token);
        }catch(SignatureException e){
            throw new JwtException("Invalid JWT signature");
        }catch (JwtException e){
            throw new JwtException("Invalid JWT token");
        }
    }
}
