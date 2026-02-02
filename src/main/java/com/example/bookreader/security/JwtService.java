package com.example.bookreader.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final long EXPIRATION_TIME = 1000*60*60*24;
    private final Key secretKey= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
    public String extractLogin(String token) {
        return getClaims(token).getSubject();
    }
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isTokenValid(String token) {
        try{
            getClaims(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
