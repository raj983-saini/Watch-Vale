package com.example.Titan.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyOtpDto {
    @NotNull(message = "OTP is required.")
    @Size(min = 4, max = 4, message = "OTP must be exactly 4 digits.")
    @Pattern(regexp = "\\d{4}", message = "OTP must be numeric.")
    private String otp;
    @NotBlank(message = "State cannot be blank or null.")
    private String state;
    private String mobile;
}
