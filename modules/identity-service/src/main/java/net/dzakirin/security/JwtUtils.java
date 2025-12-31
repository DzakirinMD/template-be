package net.dzakirin.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import net.dzakirin.exception.TokenValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    @Value("${application.security.jwt.expiration}")
    private int jwtExpirationMs;

    private Key key() {
        return Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(jwtSecret));
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        String roles = userPrincipal.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .claim("email", userPrincipal.getUsername())  // Store Email as a claim
                .claim("roles", roles)
                .setIssuer(appName)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new TokenValidationException("Invalid JWT signature: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new TokenValidationException("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new TokenValidationException("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new TokenValidationException("JWT claims string is empty: " + e.getMessage());
        }
    }
}