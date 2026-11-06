package com.example.rudigitalapi.users.infra.repositories;

import com.example.rudigitalapi.users.infra.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);
    boolean existsByLogin(String login);
}
