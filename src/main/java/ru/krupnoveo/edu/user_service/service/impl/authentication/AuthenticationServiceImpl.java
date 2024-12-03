package ru.krupnoveo.edu.user_service.service.impl.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.krupnoveo.edu.user_service.api.dto.request.AuthenticationRequest;
import ru.krupnoveo.edu.user_service.api.dto.request.CreateUserRequest;
import ru.krupnoveo.edu.user_service.api.dto.response.UserDetailsResponse;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;
import ru.krupnoveo.edu.user_service.api.exception.UserNotFoundException;
import ru.krupnoveo.edu.user_service.domain.entity.UserEntity;
import ru.krupnoveo.edu.user_service.domain.repository.JpaUserRepository;
import ru.krupnoveo.edu.user_service.service.AuthenticationService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JpaUserRepository jpaUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    @Override
    public UserResponse signUp(CreateUserRequest createUserRequest, String role) {
        if (jpaUserRepository.existsByEmail(createUserRequest.email())) {
            return authenticate(new AuthenticationRequest(createUserRequest.email(), createUserRequest.password()));
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(createUserRequest.firstName());
        userEntity.setLastName(createUserRequest.lastName());
        userEntity.setEmail(createUserRequest.email());
        userEntity.setPassword(
                passwordEncoder.encode(createUserRequest.password())
        );
        userEntity.setPhoneNumber(createUserRequest.phoneNumber());
        userEntity.setRole(role);
        userEntity.setBarbershopId(createUserRequest.barbershopId());
        userEntity.setDateOfBirth(createUserRequest.dateOfBirth());
        userEntity.setGrade(createUserRequest.grade());
        userEntity.setAboutMe(createUserRequest.aboutMe());
        return jpaUserRepository.save(userEntity).toDto()
                .jwtToken(jwtService.generateToken(userEntity.getEmail(), userEntity.getId()))
                .build();
    }

    @Override
    public UserResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        UserEntity user = jpaUserRepository.findByEmail(request.email()).orElseThrow(UserNotFoundException::new);
        return user.toDto()
                .jwtToken(jwtService.generateToken(user.getEmail(), user.getId()))
                .build();

    }

    @Override
    public UserDetailsResponse isTokenValid(String token) {
        try {
            String email = jwtService.extractUsername(token);
            UUID id = jwtService.extractUserId(token);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if (jwtService.isTokenValid(token, userDetails)) {
                return new UserDetailsResponse(
                        id,
                        userDetails.getUsername(),
                        userDetails.getAuthorities()
                );
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
