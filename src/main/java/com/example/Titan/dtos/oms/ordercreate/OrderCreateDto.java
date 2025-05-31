package com.example.Titan.dtos.oms.ordercreate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreateDto {
    private Long id;
    private Long addressId;
    private Double totalAmount;
}
