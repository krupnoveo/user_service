package ru.krupnoveo.edu.user_service.api.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
