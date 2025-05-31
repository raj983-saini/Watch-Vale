package com.example.Titan.daos.auth;

import com.example.Titan.entity.user.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthDao extends JpaRepository<UserAuth,Long> {
    Optional<UserAuth> findByCodeOrderByCreatedAtDesc(String code);

    Optional<UserAuth> findByInternalToken(String internalToken);

    @Query(nativeQuery = true,value="select count(1) from titan.user_auth where mobile=:mobile and created_at >= DATE_SUB(NOW(), INTERVAL 1 HOUR)")
    Long getCountUserAuthByMobile(String mobile);
}
