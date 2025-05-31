package com.example.Titan.daos.oms.cart;

import com.example.Titan.entity.oms.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemDao extends JpaRepository<CartItem , Long> {
    @Query("SELECT c FROM CartItem c WHERE c.cart.id = :cartId AND c.watch.id = :watchId")
    Optional<CartItem> findByCartIdAndWatchId(@Param("cartId") Long cartId, @Param("watchId") Long watchId);
}
