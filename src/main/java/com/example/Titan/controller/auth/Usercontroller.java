package com.example.Titan.controller.auth;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.dtos.auth.UserAuthCreate;
import com.example.Titan.dtos.auth.VerifyOtpDto;
import com.example.Titan.externalservice.processor.SmsProcessor;
import com.example.Titan.service.auth.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/t1/auth")
public class Usercontroller {
private final UserService userService;

    public Usercontroller(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponseDto> createUser(@RequestBody UserAuthCreate userAuthCreate) throws Exception {
        log.info("data 0 {} " ,userAuthCreate);
        try {
            String intent = userService.getIntent(userAuthCreate);
            log.info("data 1 {} " ,intent);
            ResponseEntity<ApiResponseDto> ok = ResponseEntity.ok(ApiResponseDto.success(intent));
            log.info("data {} " ,ok);
            return ok;
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseDto.error("Something wrong",userService.getIntent(userAuthCreate)));
        }
    }
    @PostMapping(value = "/verify/otp")
    public ResponseEntity<ApiResponseDto> verifyotp(@RequestBody VerifyOtpDto verifyOtpDto) throws Exception {
        try {
            log.info("verify {}" ,verifyOtpDto);
            return ResponseEntity.ok(ApiResponseDto.success(userService.verifyOtp(verifyOtpDto)));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseDto.error("Something wrong",userService.verifyOtp(verifyOtpDto)));
        }
    }

}
