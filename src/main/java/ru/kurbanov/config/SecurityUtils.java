package ru.kurbanov.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    public UUID getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            String subject = jwtAuthenticationToken.getToken().getSubject();
            return UUID.fromString(subject);
        }
        try {
            return UUID.fromString(auth.getName());
        } catch (IllegalArgumentException e) {
            return UUID.fromString("00000000-0000-0000-0000-000000000001");
        }
    }

    public boolean hasRole(String role) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}
