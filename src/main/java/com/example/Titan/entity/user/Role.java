package com.example.Titan.entity.user;

import com.example.Titan.entity.LongBasid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "role")
public class Role extends LongBasid {

   @Column(name = "allow_b2b_login")
   private  boolean allowB2BMethod;
   @Column(name = "allow_otp_login")
   private boolean allowOtpLogin;
   @Column(name = "type")
   private String type;
   @Column(name = "name")
   private String name;
}
