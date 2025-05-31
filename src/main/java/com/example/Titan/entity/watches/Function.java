package com.example.Titan.entity.watches;

import com.example.Titan.entity.LongBasid;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "watch") // Prevent recursion
@Table(name = "watch_functions")
public class Function extends LongBasid {

        @Column(name = "function_name", nullable = false)
        private String functionName;

        @ManyToOne
        @JoinColumn(name = "watch_id", nullable = false)
        @ToString.Exclude  // Prevents infinite recursion in toString()
        @JsonBackReference
        private Watches watch;
}
