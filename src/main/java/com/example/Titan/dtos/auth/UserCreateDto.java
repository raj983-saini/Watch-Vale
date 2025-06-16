package com.example.Titan.dtos.auth;

import lombok.Data;
import java.time.LocalDate;
@Data
public class UserCreateDto {
        private String email;
        private String mobile;
        private String name;
         private LocalDate dob;
         private String profileUrl;

}

