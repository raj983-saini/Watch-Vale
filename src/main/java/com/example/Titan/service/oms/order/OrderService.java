package com.example.Titan.service.oms.order;

import com.example.Titan.dtos.oms.ordercreate.OrderCreateDto;
import com.example.Titan.entity.oms.Orders;
import com.example.Titan.entity.user.User;
import com.example.Titan.expections.CustomException;
import com.example.Titan.service.oms.omsutils.OrderUtils;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OrderService {
    private final OrderUtils orderUtils;

    public OrderService(OrderUtils orderUtils) {
        this.orderUtils = orderUtils;
    }
 public  String createOrderById(User user, OrderCreateDto createDto) throws RazorpayException, CustomException {
       return orderUtils.createOrderById(user,createDto);
 }
 public  String createOrderByCart(User user,OrderCreateDto createDto) throws RazorpayException, CustomException {
       return orderUtils.createOrderByCart(user.getId(),createDto);
 }
 public List<Orders> getAllOrder(User user){
        return orderUtils.getAll(user);
 }
}
