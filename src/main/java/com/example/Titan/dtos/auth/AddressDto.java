package com.example.Titan.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {
    private String fullName;
    private String mobile;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String country;
    private String state;
}
