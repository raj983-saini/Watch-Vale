package com.example.Titan.entity.watches;

import com.example.Titan.entity.LongBasid;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "watch_tag")
public class WatchTag extends LongBasid {
    @Column(name = "tag")
    private String tag;

    @ManyToOne
    @JoinColumn(name = "watch_id")
    @JsonBackReference
    @ToString.Exclude
    private Watches watch;
}
