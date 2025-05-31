package com.example.Titan.razorPay.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;

public class RazorpaySignatureUtil {

    public static boolean verifySignature(String orderId, String paymentId, String razorpaySignature, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        String data = orderId + "|" + paymentId;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        Formatter formatter = new Formatter();
        for (byte b : hmacBytes) {
            formatter.format("%02x", b);
        }
        String generatedSignature =  formatter.toString();
        return generatedSignature.equals(razorpaySignature);
    }
}

