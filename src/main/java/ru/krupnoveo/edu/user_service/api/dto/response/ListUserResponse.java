package ru.krupnoveo.edu.user_service.api.dto.response;

import java.util.List;

public record ListUserResponse(
        List<UserResponse> users
) {
}
