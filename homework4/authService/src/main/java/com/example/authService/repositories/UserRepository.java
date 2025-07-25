package com.example.authService.repositories;


import com.example.authService.models.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    List<UserEntity> findAll(Pageable pageable);
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByLoginAndPassword(String login, String password);
}
