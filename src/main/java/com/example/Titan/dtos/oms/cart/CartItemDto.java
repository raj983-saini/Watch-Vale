package com.example.Titan.dtos.oms.cart;

import com.example.Titan.entity.watches.Watches;
import lombok.Data;

@Data
public class CartItemDto {
    private Watches watchId;
    private int quantity;
}
