package com.example.Titan.dtos.auth;

import com.example.Titan.constant.ChannelEnum;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserAuthCreate {
    private Long userId;
    private String code;
    private String internalToken;
    private ChannelEnum channel;
    private String status;
    private LocalDateTime expiredAt;
    private String mobile;
}
