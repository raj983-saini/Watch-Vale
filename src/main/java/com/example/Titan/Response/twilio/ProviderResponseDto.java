package com.example.Titan.Response.twilio;

import lombok.Data;
import org.aspectj.bridge.Message;

import java.util.Map;

@Data
public class ProviderResponseDto {
    private Map<String, Object> res;
    private Map<String, Object> req;
    private String providerId;
    private String message;
    private String status;
    private String providerMessageId;
}