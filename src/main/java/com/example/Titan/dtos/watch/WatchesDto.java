package com.example.Titan.dtos.watch;

import lombok.Data;

import java.util.List;

@Data
public class WatchesDto {
    private String brand;
    private String type;
    private List<String> image;
    private String name;
    private String description;
    private Long rating;
    private double price;
    private String strapColor;
    private String strapMaterial;
    private String strapShape;
    private String dialSize;
    private String dialColor;
    private String dialShape;
    private String strapSize;
    private String material;
    private double discount;
    private List<String> functions;
    private String screenSize;
    private String batteryLife;
    private int warrantyPeriod;
    private boolean isavailabile;
    private boolean isBluetooth;
}
