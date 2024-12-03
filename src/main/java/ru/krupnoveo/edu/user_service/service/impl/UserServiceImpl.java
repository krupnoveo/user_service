package ru.krupnoveo.edu.user_service.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.krupnoveo.edu.user_service.api.dto.request.UpdatePasswordRequest;
import ru.krupnoveo.edu.user_service.api.dto.request.UpdateUserRequest;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;
import ru.krupnoveo.edu.user_service.api.exception.UserNotFoundException;
import ru.krupnoveo.edu.user_service.domain.entity.UserEntity;
import ru.krupnoveo.edu.user_service.domain.repository.JpaUserRepository;
import ru.krupnoveo.edu.user_service.service.ObjectStorageService;
import ru.krupnoveo.edu.user_service.service.UserService;
import ru.krupnoveo.edu.user_service.service.impl.authentication.JwtService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final JpaUserRepository jpaUserRepository;
    private final ObjectStorageService objectStorageService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponse getUserById(String token) {
        return getUser(token).toDto().build();
    }

    @Override
    public UserResponse getUserById(UUID id) {
        return getUser(id).toDto().build();
    }

    @Override
    public List<UserResponse> getAllUsers(UUID barbershopId, String role) {
        if (role != null && !role.isBlank()) {
            if (role.equals("BARBER") && barbershopId != null) {
                return jpaUserRepository
                        .findAllByBarbershopIdAndRole(barbershopId, role)
                        .stream()
                        .map(a -> a.toDto().build())
                        .toList();
            }
            return jpaUserRepository
                    .findAllByRole(role)
                    .stream()
                    .map(a -> a.toDto().build())
                    .toList();
        }
        return jpaUserRepository
                .findAll()
                .stream()
                .map(a -> a.toDto().build())
                .toList();
    }

    @Override
    public UserResponse deleteUser(String token) {
        deletePhoto(token);
        UserEntity user = getUser(token);
        jpaUserRepository.delete(user);
        return user.toDto().build();
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest, String token) {
        UserEntity user = getUser(token);
        user.setFirstName(updateUserRequest.firstName());
        user.setLastName(updateUserRequest.lastName());
        user.setEmail(updateUserRequest.email());
        user.setPhoneNumber(updateUserRequest.phoneNumber());
        user.setDateOfBirth(updateUserRequest.dateOfBirth());
        user.setBarbershopId(updateUserRequest.barbershopId());
        user.setRole(updateUserRequest.role());
        user.setGrade(updateUserRequest.grade());
        user.setAboutMe(updateUserRequest.aboutMe());
        return jpaUserRepository.save(user).toDto().build();
    }

    @Override
    public UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, String token) {
        UserEntity user = getUser(token);
        user.setPassword(
                passwordEncoder.encode(updatePasswordRequest.password())
        );
        return jpaUserRepository.save(user).toDto().build();
    }

    @Override
    public InputStreamResource getPhoto(String token) {
        UserEntity user = getUser(token);
        return objectStorageService.downloadPhoto(user.getPhoto());
    }

    @Override
    public void setPhoto(MultipartFile file, String token) {
        UserEntity user = getUser(token);
        String fileName = objectStorageService.uploadPhoto(file, user.getPhoto());
        user.setPhoto(fileName);
        jpaUserRepository.save(user);
    }

    @Override
    public void deletePhoto(String token) {
        UserEntity user = getUser(token);
        objectStorageService.deletePhoto(user.getPhoto());
        user.setPhoto(null);
        jpaUserRepository.save(user);
    }

    private UserEntity getUser(String token) {
        UUID id = jwtService.extractUserId(token);
        return getUser(id);
    }

    private UserEntity getUser(UUID id) {
        Optional<UserEntity> user = jpaUserRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException(id));
    }
}
