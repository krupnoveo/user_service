package ru.krupnoveo.edu.user_service.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiErrorResponse(
        @JsonProperty("status_code") int statusCode,
        String message
) {
}
