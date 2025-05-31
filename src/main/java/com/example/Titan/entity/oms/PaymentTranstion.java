package com.example.Titan.entity.oms;

import com.example.Titan.entity.LongBasid;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "payment_transtion")
public class PaymentTranstion extends LongBasid {

    @Column(name = "entity_type")
    private String entity;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "transtion_order_id")
    private String transtionOrderId;

    @Column(name = "amount_paid")
    private Double amountPaid;

    @Column(name = "amount_due")
    private Double amountDue;

    @Column(name = "currency")
    private String currency;

    @Column(name = "receipt")
    private String receipt;

    @Column(name = "offer_id")
    private String offerId;

    @Column(name = "status")
    private String status;

    @Column(name = "attempts")
    private Integer attempts;

    @Column(name = "order_created_at", nullable = false)
    private LocalDateTime createdAt;
}
