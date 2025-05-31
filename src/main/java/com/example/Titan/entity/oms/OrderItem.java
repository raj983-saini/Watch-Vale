package com.example.Titan.entity.oms;

import com.example.Titan.entity.LongBasid;
import com.example.Titan.entity.watches.Watches;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItem extends LongBasid {

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "watch_id", nullable = false)
    @ToString.Exclude
    private Watches watch;

    private Integer quantity;
    private Double price;
}
