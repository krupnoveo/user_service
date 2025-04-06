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

    UserResponse deleteUserByToken(String token);

    UserResponse updateUserByToken(UpdateUserRequest updateUserRequest, String token);

    UserResponse updatePasswordByToken(UpdatePasswordRequest updatePasswordRequest, String token);

    InputStreamResource getPhotoByToken(String token);

    void setPhotoByToken(MultipartFile file, String token);

    void deletePhotoByToken(String token);

    UserResponse updateUserById(UpdateUserRequest updateUserRequest, UUID id);

    UserResponse updatePasswordById(UpdatePasswordRequest updatePasswordRequest, UUID id);

    InputStreamResource getPhotoById(UUID id);

    void setPhotoById(MultipartFile file, UUID id);

    void deletePhotoById(UUID id);

    UserResponse deleteUserById(UUID id);
}
