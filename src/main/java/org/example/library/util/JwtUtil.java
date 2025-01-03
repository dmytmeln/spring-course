package org.example.library.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.example.library.domain.Role;
import org.example.library.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${myjwttoken.app.jwtSecret}")
    private String jwtSecret;

    @Value("${myjwttoken.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSecretKey())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .compact();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parse(authToken)
                    .getBody();
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("Signature validation failed: " + e);
        }

        return false;
    }

    public User getUserByToken(String token) {
        var claims = parseClaims(token);
        return User.builder()
                .id(claims.get("id", Integer.class))
                .email(claims.getSubject())
                .role(Role.valueOf(claims.get("role", String.class)))
                .build();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
