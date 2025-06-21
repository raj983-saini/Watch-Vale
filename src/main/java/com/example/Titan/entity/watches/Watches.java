package com.example.Titan.entity.watches;

import com.example.Titan.entity.LongBasid;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true, exclude = "functions")
@Table(name = "Watches")
public class Watches extends LongBasid {


    @Column(name = "brand")
    private String brand;

    @Column(name = "watch_images")
    private String  watch_images;

    @Column(name = "watch_type")
    private String type;

    @OneToMany(mappedBy = "watch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<WatchImage> image = new ArrayList<>();

    @Column(name = "watch_name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;  // New: Watch description

    @Column(name = "rating")
    private Long rating;

    @Column(name = "price")
    private double price;

    @Column(name = "strap_color")
    private String strapColor;

    @Column(name = "strap_material")
    private String strapMaterial;

    @Column(name = "strap_shape")
    private String strapShape;

    @Column(name = "dial_size")
    private String dialSize;

    @Column(name = "dial_color")
    private String dialColor;

    @Column(name = "dial_shape")
    private String dialShape;

    @Column(name = "strap_size")
    private String strapSize;

    @Column(name = "material")
    private String material;

    @Column(name = "discount")
    private double discount;

    @OneToMany(mappedBy = "watch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude // Prevent recursion
    @JsonManagedReference
    private List<Function> functions = new ArrayList<>();

    public void setFunctions(List<Function> newFunctions) {
        this.functions.clear();  // Ensures orphan removal works
        if (newFunctions != null) {
            newFunctions.forEach(func -> func.setWatch(this)); // Maintain relationship
            this.functions.addAll(newFunctions);
        }
    }

    @Column(name = "screen_size")
    private String screenSize;

    @Column(name = "battery_life")  // New: Battery life in hours/days
    private String batteryLife;

    @Column(name = "warranty_period")  // New: Warranty in years
    private int warrantyPeriod;

    @Column(name = "availability_status")  // New: In stock or not
    private boolean availabilityStatus;

    @Column(name = "is_bluetooth")
    private boolean isBluetooth;

    @OneToMany(mappedBy = "watch", cascade = CascadeType.ALL)
    private List<WatchTag> tags = new ArrayList<>();
}
