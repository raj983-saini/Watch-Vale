package com.example.Titan.utils;

import com.example.Titan.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtUtils {
    @Value("${jwt.secret:-}")
    private String base64Secret;

    private static Key secretKey;

    @PostConstruct
    public void init() {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(base64Secret);
        secretKey = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());
    }
    @Value("${jwt.secret:-}")
    private static String jwtSecret;

    public static  String genrateToken(User user ,long expirationTime){
        Map<String , Object> claims = new HashMap<>();
        claims.put("user_id" , user.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
    public static String generateRefreshToken(User user,long expirationTime) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 30 days
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String extractUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    public boolean validateToken(String token, User user) {
        try {
            String userId = extractUserId(token);
            return (userId.equals(String.valueOf(user.getId())) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
}
