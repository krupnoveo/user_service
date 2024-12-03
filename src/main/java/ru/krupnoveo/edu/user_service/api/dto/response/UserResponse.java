package ru.krupnoveo.edu.user_service.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;


@Builder
public record UserResponse(
        UUID id,
        @JsonProperty("jwt_token") String jwtToken,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        String email,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("about_me") String aboutMe,
        String role,
        @JsonProperty("date_of_birth") OffsetDateTime dateOfBirth,
        @JsonProperty("barbershop_id") UUID barbershopId,
        String grade
        ) {
}
