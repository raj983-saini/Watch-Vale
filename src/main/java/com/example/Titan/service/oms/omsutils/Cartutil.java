package com.example.Titan.service.oms.omsutils;

import com.example.Titan.daos.auth.AddressDao;
import com.example.Titan.daos.oms.cart.CartDao;
import com.example.Titan.daos.oms.cart.CartItemDao;
import com.example.Titan.daos.watches.WatchDao;
import com.example.Titan.dtos.oms.cart.CartDto;
import com.example.Titan.dtos.oms.cart.CartItemDto;
import com.example.Titan.entity.oms.cart.Cart;
import com.example.Titan.entity.oms.cart.CartItem;
import com.example.Titan.entity.user.Address;
import com.example.Titan.entity.user.User;
import com.example.Titan.entity.watches.Watches;
import com.example.Titan.expections.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class Cartutil {

    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final WatchDao watchDao;
    private final AddressDao addressDao;

    public Cartutil(CartDao cartDao, CartItemDao cartItemDao, WatchDao watchDao, AddressDao addressDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.watchDao = watchDao;
        this.addressDao = addressDao;
    }

    public String createCart(User user, Long watchId) throws CustomException {

        Optional<Watches> watchById = watchDao.findById(watchId);
        if (watchById.isEmpty()){
            throw  new CustomException(HttpStatus.NOT_FOUND ,"Watch not found ");
        }
        Watches watch = watchById.get();
        Optional<Cart> existingCartOptional = cartDao.findByUserId(user.getId());

        Cart cart;
        if (existingCartOptional.isPresent()) {

            cart = existingCartOptional.get();
            log.info("Existing Cart Found: {}", cart);
        } else {

            cart = new Cart();
            cart.setUserId(user.getId());
            cart.setTotalPrice(0.0);
        }
        cart.setDeliveryFee(10.0);
        cart.setDiscountPercentage(watch.getDiscount());

        Map<Long, CartItem> existingCartItemsMap = cart.getWatches().stream()
                .collect(Collectors.toMap(item -> item.getWatch().getId(), item -> item));


            if (existingCartItemsMap.containsKey(watchId)) {

                CartItem existingItem = existingCartItemsMap.get(watchId);
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {

                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setWatch(watch);
                cartItem.setQuantity(1);
                cart.getWatches().add(cartItem);
            }

        Address address = addressDao.findDefaultAddress(user.getId());
        cart.setAddressId(address.getId());

        cartDao.save(cart);
        updatePrize(cart.getId());

        return "Item Added Successfully To Cart";
    }

    public CartDto getCart(User user) {
        Optional<Cart> cartOptional = cartDao.findByUserId(user.getId());

        if (cartOptional.isEmpty() || cartOptional.get().getWatches().isEmpty()) {
            return new CartDto(user.getId(), new ArrayList<>(),0.0,0.0,0.0,0,0.0);
        }

        Cart cart = cartOptional.get();
        updatePrize(cart.getId());

        CartDto responseDto = new CartDto();
        responseDto.setDeliveryFee(cart.getDeliveryFee());
        responseDto.setDiscountPercentage(cart.getDiscountPercentage());
        responseDto.setTotalPayablePrize(cart.getTotalPayablePrice());
        responseDto.setUserId(cart.getUserId());
        responseDto.setAddress(cart.getAddressId());
        responseDto.setTotalPrize(cart.getTotalPrice());
        responseDto.setCartItem(cart.getWatches().stream().map(cartItem -> {
            CartItemDto dto = new CartItemDto();
            Optional<Watches> watch = watchDao.findById(cartItem.getWatch().getId());
            dto.setWatchId(watch.get());
            dto.setQuantity(cartItem.getQuantity());
            return dto;
        }).collect(Collectors.toList()));

        return responseDto;
    }

    public String updateAddQuantity(User user, Long id ) {
        Optional<Cart> cart = cartDao.findByUserId(user.getId());
        if (cart.isEmpty()) {
            return "Cart not found";
        }

        Optional<CartItem> cartItem = cartItemDao.findByCartIdAndWatchId(cart.get().getId(), id);
        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            item.setQuantity(item.getQuantity() + 1);
            item.setItems_prize(item.getWatch().getPrice() * item.getQuantity());
            cartItemDao.save(item);
        } else {
            return "Item not found in cart";
        }

        updatePrize(cart.get().getId());
        return "Quantity updated successfully";
    }

    public String updateRemoveQuantity(User user, Long id ) {
        Optional<Cart> cart = cartDao.findByUserId(user.getId());
        if (cart.isEmpty()) {
            return "Cart not found";
        }

        Optional<CartItem> cartItem = cartItemDao.findByCartIdAndWatchId(cart.get().getId(), id);
        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            if (item.getQuantity() != 1){
            item.setQuantity(item.getQuantity() - 1);
            item.setItems_prize(item.getWatch().getPrice() * item.getQuantity());
            cartItemDao.save(item);}
            else {
                cartItemDao.delete(item);
            }
        } else {
            return "Item not found in cart";
        }

        updatePrize(cart.get().getId());
        return "Quantity updated successfully";
    }

    private void updatePrize(Long cartId) {
        Optional<Cart> cartOptional = cartDao.findById(cartId);
        if (cartOptional.isEmpty()) {
            log.warn("Cart with ID {} not found", cartId);
            return;
        }

        Cart cart = cartOptional.get();
        cart.setTotalPrice(0.0); // Reset total price

        log.info("get item {}", cart.getWatches());

        if (!cart.getWatches().isEmpty()) {
            cart.getWatches().forEach(item -> {
                Double price = watchDao.findPriceByWatchId(item.getWatch().getId());
                if (price != null) {
                    log.info("Price for watch {}: {}", item.getWatch().getId(), price);
                    item.setItems_prize(price * item.getQuantity());
                    cart.setTotalPrice(cart.getTotalPrice() + item.getItems_prize());
                    double discountAmount = (item.getWatch().getDiscount() / 100) * cart.getTotalPrice();
                    discountAmount = discountAmount*item.getQuantity();
                    cart.setTotalPayablePrice(cart.getTotalPrice() - discountAmount);
                }
            });
            // Correct discount calculation
            cart.setTotalPayablePrice(cart.getTotalPayablePrice()+ cart.getDeliveryFee());
        } else {
            // Reset cart values if no items are present
            cart.setTotalPayablePrice(0.0);
            cart.setDeliveryFee(0.0);
            cart.setDiscountPercentage(0.0);
            cart.setTotalPrice(0.0);
        }

        cartDao.save(cart);
    }
   public String changeAddress(Long addressId , Long userId) throws CustomException {
       Optional<Cart> cart = cartDao.findByUserId(userId);
       if (cart.isEmpty()){
           throw new CustomException("Cart Not Found");
       }
       cart.get().setAddressId(addressId);
       cartDao.save(cart.get());
       return "Change Address Suuccfully";
   }
}
