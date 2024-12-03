package ru.krupnoveo.edu.user_service.api.dto.request;

import java.util.UUID;

public record UpdatePasswordRequest(
        String password
) {}
