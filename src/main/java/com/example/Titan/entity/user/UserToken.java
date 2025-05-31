package com.example.Titan.entity.user;

import com.example.Titan.entity.LongBasid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Table(name = "user_tokens")
@Data
@Entity
public class UserToken extends LongBasid {
    @Column(name="user_id")
    private Long userId;

    @Column(name="access_token")
    private String accessToken;

    @Column(name="refresh_token")
    private String refreshToken;

    @Column(name="status")
    private String status;

    @Column(name="access_token_expiration")
    private LocalDateTime accessTokenExpiration;

    @Column(name="refresh_token_expiration")
    private LocalDateTime refreshTokenExpiration;
}