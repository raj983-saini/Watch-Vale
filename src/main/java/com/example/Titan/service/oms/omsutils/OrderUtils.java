package com.example.Titan.service.oms.omsutils;

import com.example.Titan.constant.OrderStatus;
import com.example.Titan.daos.oms.cart.CartDao;
import com.example.Titan.daos.oms.order.OrderDao;
import com.example.Titan.daos.watches.WatchDao;
import com.example.Titan.dtos.oms.ordercreate.OrderCreateDto;
import com.example.Titan.entity.oms.OrderItem;
import com.example.Titan.entity.oms.Orders;
import com.example.Titan.entity.oms.cart.Cart;
import com.example.Titan.entity.user.User;
import com.example.Titan.entity.watches.Watches;
import com.example.Titan.expections.CustomException;
import com.example.Titan.razorPay.dto.PaymentTranstionDto;
import com.example.Titan.razorPay.service.PaymentService;
import com.example.Titan.utils.UniqueIdGenerationUtil;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderUtils {
    private final OrderDao orderDao;
    private final CartDao cartDao;
    private final WatchDao watchDao;
    private final UniqueIdGenerationUtil uniqueIdGenerationUtil;
    private final PaymentService paymentService;

    public OrderUtils(OrderDao orderDao, CartDao cartDao, WatchDao watchDao, UniqueIdGenerationUtil uniqueIdGenerationUtil, PaymentService paymentService) {
        this.orderDao = orderDao;
        this.cartDao = cartDao;
        this.watchDao = watchDao;
        this.uniqueIdGenerationUtil = uniqueIdGenerationUtil;
        this.paymentService = paymentService;
    }

    public String createOrderByCart(Long userId,OrderCreateDto createDto) throws RazorpayException {
        Optional<Cart> cartOptional = cartDao.findByUserId(userId);

        if (cartOptional.isEmpty() || cartOptional.get().getWatches().isEmpty()) {
            return "Cart is Empty, Please Add Items First";
        }

        Cart cart = cartOptional.get();
        Orders orders = new Orders();
        orders.setOrderNo(uniqueIdGenerationUtil.generateId());
        orders.setUserId(cart.getUserId());
        orders.setDeliveryFee(cart.getDeliveryFee() != null ? cart.getDeliveryFee() : 0.0);
        orders.setTotalPayablePrice(cart.getTotalPayablePrice() != null ? cart.getTotalPayablePrice() : 0.0);
        orders.setDiscountPercentage(cart.getDiscountPercentage() != null ? cart.getDiscountPercentage() : 0.0);
        orders.setTotalPrice(cart.getTotalPrice());
        orders.setAddressId(createDto.getAddressId());

        double discountAmount = (cart.getTotalPrice() * orders.getDiscountPercentage()) / 100;
        orders.setEffectivePrice(discountAmount);
        orders.setStatus(OrderStatus.CREATED); // Default order status

        orders.setWatches(cart.getWatches().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orders);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getItems_prize());
            orderItem.setWatch(cartItem.getWatch());
            return orderItem;
        }).collect(Collectors.toList()));

        cartDao.delete(cart);
    return    generatePayment(orders);

    }
    public String createOrderById(User user , OrderCreateDto createDto) throws CustomException, RazorpayException {
        Optional<Watches> watchById = watchDao.findById(createDto.getId());
        if (watchById.isEmpty()){
            throw  new CustomException("watch not  found");
        }
        Watches watch = watchById.get();
        Orders orders = new Orders();
        orders.setOrderNo(uniqueIdGenerationUtil.generateId());
        orders.setUserId(user.getId());
        orders.setDeliveryFee( 10D);

        orders.setDiscountPercentage(watch.getDiscount());
        orders.setTotalPrice(createDto.getTotalAmount());
        orders.setAddressId(createDto.getAddressId());

        double discountAmount = (watch.getPrice() * orders.getDiscountPercentage()) / 100;
        orders.setEffectivePrice(discountAmount);
        orders.setTotalPayablePrice(orders.getTotalPrice() - discountAmount);
        orders.setStatus(OrderStatus.CREATED); // Default order status

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orders);
            orderItem.setQuantity(1);
            orderItem.setPrice(watch.getPrice());
            orderItem.setWatch(watch);
            orders.setWatches(Collections.singletonList((orderItem)));

       return    generatePayment(orders);
    }

    public String generatePayment(Orders orders) throws RazorpayException {
        PaymentTranstionDto dto =new PaymentTranstionDto();
        dto.setOrderId(orders.getOrderNo());
        dto.setAmount(orders.getTotalPrice());
        log.info("Order Details: {}", orders);
        orderDao.save(orders);
      return  paymentService.createPayment(dto);

    }

//    public String makeOrder(List<Watches> watches)
    public List<Orders> getAll(User user){
     return orderDao.findByUserId(user.getId());
    }
}
