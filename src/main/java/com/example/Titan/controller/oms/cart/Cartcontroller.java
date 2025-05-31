package com.example.Titan.controller.oms.cart;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.dtos.oms.cart.CartDto;
import com.example.Titan.entity.user.User;
import com.example.Titan.expections.CustomException;
import com.example.Titan.service.oms.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/t1/cart/")
public class Cartcontroller {
    private final CartService cartService;

    public Cartcontroller(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping(value = "create")
    private ResponseEntity<ApiResponseDto> createCart(@RequestParam Long watchId ,@RequestAttribute User user) throws CustomException {
        return ResponseEntity.ok(ApiResponseDto.success(cartService.createCart(user,watchId)));
    }
    @GetMapping(value = "")
    private ResponseEntity<ApiResponseDto> getCart(@RequestAttribute User user) throws CustomException {
        return ResponseEntity.ok(ApiResponseDto.success(cartService.getCart(user)));
    }
    @PutMapping(value = "add")
    private ResponseEntity<ApiResponseDto> addQuantity(@RequestAttribute User user , @RequestParam("id") Long id){
        return ResponseEntity.ok(ApiResponseDto.success(cartService.updateAddQunatity(user,id)));
    }
    @PutMapping(value = "remove")
    private ResponseEntity<ApiResponseDto> removeQuanity(@RequestAttribute User user , @RequestParam("id") Long id){
        return ResponseEntity.ok(ApiResponseDto.success(cartService.updateRemoveQunatity(user,id)));
    }
    @PutMapping(value = "address")
    private ResponseEntity<ApiResponseDto> changeAddress(@RequestParam Long addressId , @RequestAttribute(name = "user") User user) throws CustomException {
        return ResponseEntity.ok(ApiResponseDto.success(cartService.changeAddress(addressId,user)));
    }
}
