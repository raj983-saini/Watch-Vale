package com.example.Titan.entity.user;

import com.example.Titan.entity.LongBasid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Table(name = "user_auth")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserAuth extends LongBasid {
    @Column(name="user_id")
    private Long userId;

    @Column(name="code")
    private String code;

    @Column(name="internal_token")
    private String internalToken;

    @Column(name="channel")
    private String channel;

    @Column(name="status")
    private String status;

    @Column(name="expired_at")
    private LocalDateTime expiredAt;

    @Column(name="mobile")
    private String mobile;

}
