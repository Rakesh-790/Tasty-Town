package com.tastytown.backend.security.jwt;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final String JWT_SECRET = "be593a9144ce9fb0207b20113c61733fd00b46ba20c7c1f285ada554feaadfb1";

    private SecretKey getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .signWith(getKey())
                .compact();
    }

    public String getUserId(String token) { // verifyToken, verify || this method is parsing and validating the token
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getUserRoll(String token) { // for extracting the role
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role")
                .toString();
    }
}
