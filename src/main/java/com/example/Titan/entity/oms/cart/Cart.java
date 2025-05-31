package com.example.Titan.entity.oms.cart;

import com.example.Titan.entity.LongBasid;
import com.example.Titan.entity.watches.Watches;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "cart")
public class Cart extends LongBasid {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_payable_price")
    private Double totalPayablePrice;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CartItem> watches = new ArrayList<>();

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "address_id")
    private Long addressId;


}
