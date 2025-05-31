package com.example.Titan.daos.auth;

import com.example.Titan.entity.user.Address;
import com.example.Titan.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao  extends JpaRepository<Address , Long> {
    List<Address> findByUserId(Long user);
    @Query("SELECT a FROM Address a WHERE a.userId =:userId AND a.isDefault = true")
    Address findDefaultAddress(Long userId);
}
