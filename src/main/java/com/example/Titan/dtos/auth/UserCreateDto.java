package com.example.Titan.dtos.auth;

import lombok.Data;
import java.time.LocalDate;
@Data
public class UserCreateDto {
        private String email;
        private String uid;
        private String mobile;
        private  String password;
        private String name;
         private Boolean isDeleted;
         private LocalDate dob;
         private String profileUrl;

}

