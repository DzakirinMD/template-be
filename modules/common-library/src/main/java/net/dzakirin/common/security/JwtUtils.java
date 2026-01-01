package net.dzakirin.common.security;

import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Key;
import java.util.UUID;

@UtilityClass
public class JwtUtils {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;

    private Key key() {
        return Keys.hmacShaKeyFor(io.jsonwebtoken.io.Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Extracts the User ID (UUID) from the current SecurityContext.
     */
    public static UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) auth.getPrincipal();
            return UUID.fromString(user.getId());
        }
        return null;
    }

    /**
     * Extracts the User email from the current SecurityContext.
     */
    public static String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) auth.getPrincipal();
            return user.getEmail();
        }
        return null;
    }
}
