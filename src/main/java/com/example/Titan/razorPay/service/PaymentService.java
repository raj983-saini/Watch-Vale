package com.example.Titan.razorPay.service;

import com.example.Titan.razorPay.dto.PaymentTranstionDto;
import com.example.Titan.razorPay.utils.PaymentUtil;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
final public PaymentUtil paymentUtil;

    public PaymentService(PaymentUtil paymentUtil) {
        this.paymentUtil = paymentUtil;
    }
    public String createPayment(PaymentTranstionDto paymentTranstionDto) throws RazorpayException {
        return paymentUtil.createPayment(paymentTranstionDto);
    }
}
