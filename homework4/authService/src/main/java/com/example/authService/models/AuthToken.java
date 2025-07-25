package com.example.authService.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class AuthToken {
    @Id
    @UuidGenerator
    private UUID token;
    @OneToOne
    private UserEntity userEntity;
    private Instant createdAt;
    private Instant expiresAt;

    @Override
    public String toString() {
        return "AuthToken{" +
                "token=" + token +
                ", userEntity=" + userEntity +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
