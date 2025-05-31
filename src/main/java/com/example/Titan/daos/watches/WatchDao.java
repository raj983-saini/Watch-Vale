package com.example.Titan.daos.watches;

import com.example.Titan.entity.watches.Watches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchDao extends JpaRepository<Watches ,Long> {
    Optional<Watches> findByName(String name);
    @Query("SELECT w.price FROM Watches w WHERE w.id = :watchId")
    Double findPriceByWatchId(@Param("watchId") Long watchId);
}
