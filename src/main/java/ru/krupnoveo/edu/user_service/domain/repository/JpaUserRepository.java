package ru.krupnoveo.edu.user_service.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krupnoveo.edu.user_service.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findAllByRole(String role);

    List<UserEntity> findAllByBarbershopIdAndRole(UUID barbershopId, String role);

    boolean existsByEmail(String email);

    boolean existsByEmailAndId(String email, UUID id);

    Optional<UserEntity> findByEmail(String email);
}
