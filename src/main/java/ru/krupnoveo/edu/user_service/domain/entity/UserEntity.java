package ru.krupnoveo.edu.user_service.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.krupnoveo.edu.user_service.api.dto.response.UserResponse;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class UserEntity implements UserDetails {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "date_of_birth")
    private OffsetDateTime dateOfBirth;

    @Column(name = "photo")
    private String photo;

    @Column(name = "barbershop_id")
    private UUID barbershopId;

    @Column(name = "grade")
    private String grade;

    public UserResponse.UserResponseBuilder toDto() {
        return UserResponse.builder()
                .barbershopId(barbershopId)
                .dateOfBirth(dateOfBirth)
                .email(email)
                .firstName(firstName)
                .grade(grade)
                .id(id)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .role(role)
                .aboutMe(aboutMe);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (role) {
            case "ADMIN" -> List.of(
//                    new SimpleGrantedAuthority("ROLE_CLIENT"),
                    new SimpleGrantedAuthority("ROLE_ADMIN")
//                    new SimpleGrantedAuthority("ROLE_BARBER"),
//                    new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")
            );
            case "ADMINISTRATOR" -> List.of(
                    new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")
//                    new SimpleGrantedAuthority("ROLE_CLIENT"),
//                    new SimpleGrantedAuthority("ROLE_BARBER")
            );
            case "CLIENT" -> List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
            case "BARBER" -> List.of(new SimpleGrantedAuthority("ROLE_BARBER"));
            default -> throw new RuntimeException("Роль не определена: " + role);
        };
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
