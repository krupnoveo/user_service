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
    public UserResponse deleteUserByToken(String token) {
        deletePhotoByToken(token);
        UserEntity user = getUser(token);
        return deleteUser(user);
    }

    @Override
    public UserResponse deleteUserById(UUID id) {
        deletePhotoById(id);
        UserEntity user = getUser(id);
        return deleteUser(user);
    }

    private UserResponse deleteUser(UserEntity user) {
        jpaUserRepository.delete(user);
        return user.toDto().build();
    }

    @Override
    public UserResponse updateUserByToken(UpdateUserRequest updateUserRequest, String token) {
        UserEntity user = getUser(token);
        return updateUser(updateUserRequest, user);
    }

    @Override
    public UserResponse updateUserById(UpdateUserRequest updateUserRequest, UUID id) {
        UserEntity user = getUser(id);
        return updateUser(updateUserRequest, user);
    }

    private UserResponse updateUser(UpdateUserRequest updateUserRequest, UserEntity user) {
        if (updateUserRequest.firstName() != null) {
            user.setFirstName(updateUserRequest.firstName());
        }
        if (updateUserRequest.lastName() != null) {
            user.setLastName(updateUserRequest.lastName());
        }
        if (updateUserRequest.email() != null) {
            user.setEmail(updateUserRequest.email());
        }
        if (updateUserRequest.phoneNumber() != null) {
            user.setPhoneNumber(updateUserRequest.phoneNumber());
        }
        if (updateUserRequest.dateOfBirth() != null) {
            user.setDateOfBirth(updateUserRequest.dateOfBirth());
        }
        if (updateUserRequest.barbershopId() != null) {
            user.setBarbershopId(updateUserRequest.barbershopId());
        }
        if (updateUserRequest.role() != null) {
            user.setRole(updateUserRequest.role());
        }
        if (updateUserRequest.grade() != null) {
            user.setGrade(updateUserRequest.grade());
        }
        if (updateUserRequest.aboutMe() != null) {
            user.setAboutMe(updateUserRequest.aboutMe());
        }
        return jpaUserRepository.save(user).toDto().build();
    }

    @Override
    public UserResponse updatePasswordByToken(UpdatePasswordRequest updatePasswordRequest, String token) {
        UserEntity user = getUser(token);
        return updatePassword(updatePasswordRequest, user);
    }


    @Override
    public UserResponse updatePasswordById(UpdatePasswordRequest updatePasswordRequest, UUID id) {
        UserEntity user = getUser(id);
        return updatePassword(updatePasswordRequest, user);
    }

    private UserResponse updatePassword(UpdatePasswordRequest updatePasswordRequest, UserEntity user) {
        user.setPassword(
                passwordEncoder.encode(updatePasswordRequest.password())
        );
        return jpaUserRepository.save(user).toDto().build();
    }

    @Override
    public InputStreamResource getPhotoByToken(String token) {
        UserEntity user = getUser(token);
        return objectStorageService.downloadPhoto(user.getPhoto());
    }

    @Override
    public InputStreamResource getPhotoById(UUID id) {
        UserEntity user = getUser(id);
        return objectStorageService.downloadPhoto(user.getPhoto());
    }

    @Override
    public void setPhotoByToken(MultipartFile file, String token) {
        UserEntity user = getUser(token);
        setPhoto(file, user);
    }

    @Override
    public void setPhotoById(MultipartFile file, UUID id) {
        UserEntity user = getUser(id);
        setPhoto(file, user);
    }

    private void setPhoto(MultipartFile file, UserEntity user) {
        String fileName = objectStorageService.uploadPhoto(file, user.getPhoto());
        user.setPhoto(fileName);
        jpaUserRepository.save(user);
    }

    @Override
    public void deletePhotoByToken(String token) {
        UserEntity user = getUser(token);
        deletePhoto(user);
    }

    @Override
    public void deletePhotoById(UUID id) {
        UserEntity user = getUser(id);
        deletePhoto(user);
    }

    private void deletePhoto(UserEntity user) {
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
