package com.example.Titan.razorPay.utils;

import com.example.Titan.constant.PaymentStatus;
import com.example.Titan.daos.oms.order.PaymentDao;
import com.example.Titan.entity.oms.PaymentTranstion;
import com.example.Titan.razorPay.dto.PaymentTranstionDto;
import com.example.Titan.utils.UniqueIdGenerationUtil;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentUtil {

    private static final Logger log = LoggerFactory.getLogger(PaymentUtil.class);

    private final UniqueIdGenerationUtil uniqueIdGenerationUtil;
    private final PaymentDao paymentDao;
    private final RazorpayClient razorpay;

    public PaymentUtil(UniqueIdGenerationUtil uniqueIdGenerationUtil,
                       PaymentDao paymentDao,
                       @Value("${razorpay.key.id}") String keyId,
                       @Value("${razorpay.secret.key}") String secretKey) throws RazorpayException {

        this.uniqueIdGenerationUtil = uniqueIdGenerationUtil;
        this.paymentDao = paymentDao;
        this.razorpay = new RazorpayClient(keyId, secretKey);
    }

    public String createPayment(PaymentTranstionDto transactionDto) throws RazorpayException {
        String uniqueReceiptId = uniqueIdGenerationUtil.generateId();
        JSONObject orderRequest = new JSONObject();

        orderRequest.put("amount", transactionDto.getAmount() * 100); // Razorpay expects amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", transactionDto.getOrderId());

        log.info("OrderRequesst {}" , orderRequest);
        // Save initial payment transaction
        PaymentTranstion paymentTransaction = new PaymentTranstion();
        paymentTransaction.setAmount(transactionDto.getAmount());
        paymentTransaction.setCurrency("INR");
        paymentTransaction.setOrderId(transactionDto.getOrderId());
        paymentTransaction.setReceipt(uniqueReceiptId);
        paymentTransaction.setStatus(PaymentStatus.PENDING);
        paymentTransaction.setAmountPaid(0.0);
        paymentTransaction.setAmountDue(transactionDto.getAmount());
        paymentTransaction.setCreatedAt(LocalDateTime.now());

        log.info("PaymentTRansation {}" ,paymentTransaction);
        paymentDao.save(paymentTransaction);

        try {
            Order order = razorpay.orders.create(orderRequest);

            if (order != null && "created".equals(order.get("status"))) {
                paymentTransaction.setStatus(PaymentStatus.CREATED);
                paymentTransaction.setTranstionOrderId(order.get("id"));
                paymentTransaction.setAttempts(order.get("attempts"));
                paymentTransaction.setEntity(order.get("entity"));
                log.info("PaymentTRansation 2 {}" ,paymentTransaction);
                paymentDao.save(paymentTransaction); // update transaction with Razorpay details
                log.info("Razorpay order created successfully: {}", order.toString());
            } else {
                log.warn("Razorpay order status is not 'created'");
            }
           log.info("Order  {}" ,order.toString());
            return order.toString();

        } catch (RazorpayException e) {
            log.error("Razorpay order creation failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
