package com.example.Titan.dtos.oms.cart;

import com.example.Titan.entity.user.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long userId;
    private List<CartItemDto> cartItem;
    private Double totalPrize;
    private Double totalPayablePrize;
    private Double deliveryFee;
    private long address;
    private Double discountPercentage;
}
