package com.example.Titan.daos.auth;

import com.example.Titan.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByMobile(String mobile);
    @Query("SELECT u FROM User u WHERE u.mobile = :mobile AND (u.isDeleted = false OR u.isDeleted IS NULL)")
    User findByMobileAndIsDeletedNotTrue(String mobile);

    @Query("SELECT u FROM User u WHERE u.mobile = :mobile AND u.isDeleted = true")
    List<User> findByMobileAndIsDeletedTrue(String mobile);

    User findById(long id);
    boolean existsByMobileAndIsBlacklistedTrue(String mobile);
}
