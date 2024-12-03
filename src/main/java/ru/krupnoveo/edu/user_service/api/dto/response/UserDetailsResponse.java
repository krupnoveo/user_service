package ru.krupnoveo.edu.user_service.api.dto.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public record UserDetailsResponse(
        UUID id,
        String username,
        Collection<? extends GrantedAuthority> authorities
) {
}
