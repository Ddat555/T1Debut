package com.example.authService.repositories;

import com.example.authService.models.AuthToken;
import com.example.authService.models.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository extends CrudRepository<AuthToken, UUID> {
    Optional<AuthToken> findByUserEntity(UserEntity userEntity);

//    @Modifying
//    @Query("DELETE FROM AuthToken t WHERE t.expiresAt < :now")
//    void deleteExpiredTokens(@Param("now") Instant now);

    @Modifying
    @Query("DELETE FROM AuthToken t where t.userEntity.id = :userId")
    void deleteFromUserId(@Param("userId") UUID userId);
}
