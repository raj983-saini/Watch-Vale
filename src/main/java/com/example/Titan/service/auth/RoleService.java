package com.example.Titan.service.auth;

import com.example.Titan.daos.auth.RoleDao;
import com.example.Titan.entity.user.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleService {

    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getRoleType(String email) {
        return roleDao.findByType(email);
    }
    public Optional<Role> getRoleById(Long id) {
        Optional<Role> roles = roleDao.findById(id);
        log.info("roles  {}",roles);
        return roles;
    }
    public void saveRole(Role role) {
        roleDao.save(role);
    }
}
