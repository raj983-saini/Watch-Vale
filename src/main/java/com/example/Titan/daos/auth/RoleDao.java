package com.example.Titan.daos.auth;

import com.example.Titan.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RoleDao extends JpaRepository<Role,Long> {
    Role findById(long id);
    List<Role> findByType(String name);


}
