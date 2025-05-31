package com.example.Titan.daos.oms.order;

import com.example.Titan.entity.oms.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem , Long> {
}
