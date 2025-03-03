package ru.krupnoveo.edu.user_service.api.dto.response;

import java.util.List;
import java.util.UUID;

public record UserDetailsResponse(
        UUID id,
        String username,
        List<String> roles
) {
}
