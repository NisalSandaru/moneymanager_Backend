package com.nisal.moneymanager.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // ✅ Secret key for signing
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Generate JWT using email
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    // ✅ Extract email (subject) from token
    public String extractEmail(String token) {
        String cleanToken = cleanTokenPrefix(token);
        Claims claims = parseClaims(cleanToken);
        return claims.getSubject();
    }

    // ✅ Check if token is valid: not expired and belongs to user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String emailInToken = extractEmail(token);
            return emailInToken.equals(userDetails.getUsername()) && isTokenNotExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Check token expiration
    public boolean isTokenNotExpired(String token) {
        try {
            String cleanToken = cleanTokenPrefix(token);
            Claims claims = parseClaims(cleanToken);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ Parse token claims safely
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ Remove "Bearer " prefix if present
    private String cleanTokenPrefix(String token) {
        return token != null && token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
