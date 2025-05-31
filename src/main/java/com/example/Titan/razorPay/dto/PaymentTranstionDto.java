package com.example.Titan.razorPay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class PaymentTranstionDto {
    private String entity;
    private Double amount;
    private String orderId;
    private String transtionOrderId;
    private Map<String, String> notes;
    private Double amountPaid;
    private Double amountDue;
    private String currency;
    private String receipt;
    private String offerId;
    private String status;
    private Integer attempts;
    private Instant createdAt;
}
