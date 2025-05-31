package com.example.Titan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@MappedSuperclass
public class LongBasid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private  Long id;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private  LocalDateTime updateAt;
    @PrePersist
    void createDate() {
        if (createdAt == null) {
            setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        }
        setUpdateAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
    }

    @PreUpdate
    void updatedAt() {
        setUpdateAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
    }
}
