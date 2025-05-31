package com.example.Titan.daos.auth;

import com.example.Titan.entity.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokensDao extends JpaRepository<UserToken,Long> {
    Optional<UserToken> findByAccessToken(String accessToken);
    Optional<UserToken> findFirstByAccessToken(String accessToken);
    Optional<UserToken> findByRefreshTokenAndStatus(String refreshToken, String status);
}
