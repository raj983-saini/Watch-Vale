package com.example.Titan.entity.oms;

import com.example.Titan.entity.LongBasid;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "oms_orders")
@Data
@Entity
@ToString
public class Orders extends LongBasid {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address_id")
    private Long AddressId;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "total_payable_price")
    private Double totalPayablePrice;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Column(name = "user_details", columnDefinition = "LONGTEXT")
    private String userDetails;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_status")
    private String paymentStatus;;

    @Column(name = "is_tipped")
    private Boolean isTipped;

    @Column(name = "payload", columnDefinition = "LONGTEXT")
    private String payload;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private List<OrderItem> watches = new ArrayList<>();

    @Column(name = "meta_data", columnDefinition = "LONGTEXT")
    private String metaData;

    @Column(name = "effective_price")
    private Double effectivePrice;

    @Column(name = "boughtManually")
    private Boolean boughtManually;
}
