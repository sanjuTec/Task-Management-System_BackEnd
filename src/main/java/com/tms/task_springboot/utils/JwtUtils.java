package com.tms.task_springboot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    // Generate JWT token for user
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS512, getSigninKey())
                .compact();
    }

    private String getSigninKey() {
        byte[] keyBytes = Base64.getDecoder().decode("u56J7GyeZK9cstQ5zZS+lGvAC7S2Dkz2sIAtmG2TEJQ=");
        return new SecretKeySpec(keyBytes,"HmacSHA512");
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(getSigninKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    private Claims extractAllClaims(String token) {

    }
}
