package com.example.Titan.entity.watches;

import com.example.Titan.entity.LongBasid;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "watch_images")
public class WatchImage extends LongBasid {
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "watch_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Watches watch;

}
