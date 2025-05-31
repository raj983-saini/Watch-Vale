package com.example.Titan.daos.oms.order;

import com.example.Titan.entity.oms.PaymentTranstion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<PaymentTranstion , Long> {
}
