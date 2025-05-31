package com.example.Titan.entity.oms.cart;

import com.example.Titan.entity.LongBasid;
import com.example.Titan.entity.watches.Watches;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "cart_items")
public class CartItem extends LongBasid {

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    @ToString.Exclude
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "watch_id", nullable = false)
    private Watches watch;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "items_prize")
    private double items_prize;
}

