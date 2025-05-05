package com.example.student_portal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;


    @Value("${app.jwt.secret}")
    private String base64Secret;

    public SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(base64Secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String userId, String role) {

        // Generate a JWT
        String jwt = Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public String extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractStudentId(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }



    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("e " + e);
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
    }

    private boolean isExpired(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
