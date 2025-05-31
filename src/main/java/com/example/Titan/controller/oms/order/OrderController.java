package com.example.Titan.controller.oms.order;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.dtos.oms.ordercreate.OrderCreateDto;
import com.example.Titan.entity.user.User;
import com.example.Titan.expections.CustomException;
import com.example.Titan.service.oms.order.OrderService;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping(value = "t1/order")
public class OrderController {
private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponseDto> createOrderById(@RequestAttribute(name = "user")User user , @RequestBody OrderCreateDto createDto) throws RazorpayException, CustomException {
        log.info("create Order {}" , createDto);
        return ResponseEntity.ok(ApiResponseDto.success(orderService.createOrderById(user,createDto)));
    }
    @PostMapping(value = "/create/cart")
    public ResponseEntity<ApiResponseDto> createOrderByCart(@RequestAttribute(name = "user")User user , @RequestBody OrderCreateDto createDto) throws RazorpayException, CustomException {
        return ResponseEntity.ok(ApiResponseDto.success(orderService.createOrderByCart(user,createDto)));
    }
    @GetMapping(value = "")
    public ResponseEntity<ApiResponseDto> getAllOrder(@RequestAttribute(name = "user")User user ){
        return ResponseEntity.ok(ApiResponseDto.success(orderService.getAllOrder(user)));
    }
}
