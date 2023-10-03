package com.project.questapp.security;

import com.project.questapp.security.JwtUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${questapp.app.secret}")
    private String APP_SECRET;

    @Value("${questapp.expires.in}")
    private long EXPIRES_IN;

    private Key generateKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject((Long.toString(userDetails.getId()))).setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(generateKey()) // Use the secure key generated by generateKey() method
                .compact();
    }

    Long getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
