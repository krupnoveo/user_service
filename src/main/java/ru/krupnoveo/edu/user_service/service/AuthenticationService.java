package ru.krupnoveo.edu.user_service.service;

import ru.krupnoveo.edu.user_service.api.dto.request.AuthenticationRequest;
import ru.krupnoveo.edu.user_service.api.dto.request.CreateUserRequest;
import ru.krupnoveo.edu.user_service.api.dto.response.UserDetailsResponse;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;

public interface AuthenticationService {
    UserResponse signUp(CreateUserRequest createUserRequest, String role);

    UserResponse authenticate(AuthenticationRequest request);

    UserDetailsResponse isTokenValid(String token);
}
