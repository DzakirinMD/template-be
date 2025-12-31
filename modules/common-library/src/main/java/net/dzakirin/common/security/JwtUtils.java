package net.dzakirin.common.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@UtilityClass
public class JwtUtils {

    /**
     * Extracts the User ID (UUID) from the current SecurityContext.
     */
    public static UUID getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principle = auth.getName();
        return UUID.fromString(principle);
    }
}
