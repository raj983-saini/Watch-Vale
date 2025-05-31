package com.example.Titan.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
@Component
public class StringUtil {
    private static final SecureRandom secureRandom = new SecureRandom();
    public  static String getKey(int n){
        String aplhanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"abcdefghijklmnopqrstuvwxyz"+"0123456789";
        StringBuilder ab = new StringBuilder(n);
        for (int i = 0; i <n; i++) {
            int index = (int) (aplhanumeric.length()*Math.random());
            ab.append(aplhanumeric.charAt(index));
        }
        return ab.toString();
    }
    public static String generateOTP() {
        int otp = 1000 + secureRandom.nextInt(9000); // Generates a 4-digit OTP between 1000 and 9999
        return String.valueOf(otp);

    }
}
