package com.example.Titan.daos.oms.order;

import com.example.Titan.entity.oms.Orders;
import com.razorpay.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Orders ,Long> {
    List<Orders> findByUserId(Long userId);

}
