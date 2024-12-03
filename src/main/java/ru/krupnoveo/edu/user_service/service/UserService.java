package ru.krupnoveo.edu.user_service.service;


import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import ru.krupnoveo.edu.user_service.api.dto.request.*;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;


public interface UserService {

    UserResponse getUserById(String token);

    UserResponse getUserById(UUID id);

    List<UserResponse> getAllUsers(UUID barbershopId, String role);

    UserResponse deleteUser(String token);

    UserResponse updateUser(UpdateUserRequest updateUserRequest, String token);

    UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, String token);

    InputStreamResource getPhoto(String token);

    void setPhoto(MultipartFile file, String token);

    void deletePhoto(String token);
}
