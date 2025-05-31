package com.example.Titan.controller.auth;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.dtos.auth.AddressDto;
import com.example.Titan.entity.user.User;
import com.example.Titan.expections.CustomException;
import com.example.Titan.service.auth.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/t1/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponseDto> createAddress(@RequestBody AddressDto addressDto , @RequestAttribute(name = "user" )User user){
        return ResponseEntity.ok(ApiResponseDto.success(addressService.createAddress(addressDto,user)));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<ApiResponseDto> getAddressByUser(@RequestAttribute(name = "user")User user) {
        return ResponseEntity.ok(ApiResponseDto.success(addressService.getAddressByUser(user)));
    }
    @GetMapping(value = "")
    public ResponseEntity<ApiResponseDto> getAddress(@RequestParam Long addresId) throws CustomException {
        return ResponseEntity.ok(ApiResponseDto.success(addressService.getById(addresId)));
    }
}
