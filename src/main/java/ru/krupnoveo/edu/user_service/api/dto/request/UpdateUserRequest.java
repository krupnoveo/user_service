package ru.krupnoveo.edu.user_service.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateUserRequest(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        String email,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("about_me") String aboutMe,
        String role,
        @JsonProperty("date_of_birth") OffsetDateTime dateOfBirth,
        @JsonProperty("barbershop_id") UUID barbershopId,
        String grade
) {}
