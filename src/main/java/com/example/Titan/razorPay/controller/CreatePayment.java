package com.example.Titan.razorPay.controller;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.razorPay.dto.PaymentTranstionDto;
import com.example.Titan.razorPay.service.PaymentService;
import com.example.Titan.razorPay.utils.RazorpaySignatureUtil;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "t1/oms/payment")
public class CreatePayment {
    private final PaymentService paymentService;

    public CreatePayment(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<ApiResponseDto> createPayment(@RequestBody PaymentTranstionDto paymentTranstionDto) throws RazorpayException {
        log.info("Enter Create {}" , paymentTranstionDto.toString());
        return ResponseEntity.ok(ApiResponseDto.success(paymentService.createPayment(paymentTranstionDto)));
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> data) throws RazorpayException, NoSuchAlgorithmException, InvalidKeyException {
        String paymentId = data.get("razorpay_payment_id");
        String orderId = data.get("razorpay_order_id");
        String signature = data.get("razorpay_signature");

        JSONObject options = new JSONObject();
        options.put("razorpay_order_id", orderId);
        options.put("razorpay_payment_id", paymentId);
        options.put("razorpay_signature", signature);
        String secret = "your_razorpay_secret"; // from Razorpay dashboard
log.info("Data {}  {}  {}" ,paymentId,orderId,signature );
//        boolean isValid =  Utils.verifyPaymentSignature(options,secret);
        boolean isValid = RazorpaySignatureUtil.verifySignature(orderId, paymentId, signature, secret);

        if (isValid) {
            // store successful payment
            return ResponseEntity.ok("Payment Verified");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Signature");
        }
    }

}
