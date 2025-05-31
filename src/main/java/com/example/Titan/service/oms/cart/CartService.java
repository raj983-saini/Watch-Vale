package com.example.Titan.service.oms.cart;

import com.example.Titan.dtos.oms.cart.CartDto;
import com.example.Titan.entity.user.User;
import com.example.Titan.expections.CustomException;
import com.example.Titan.service.oms.omsutils.Cartutil;
import org.springframework.stereotype.Service;

@Service
public class CartService {

private final Cartutil cartutil;
    public CartService(Cartutil cartutil) {
        this.cartutil = cartutil;
    }
    public String createCart(User user ,Long watchId) throws CustomException {return  cartutil.createCart(user ,watchId);}
    public CartDto getCart(User user) throws CustomException {return  cartutil.getCart(user);}
    public String updateAddQunatity(User user , Long id){
        return  cartutil.updateAddQuantity(user,id);
    }
    public String updateRemoveQunatity(User user , Long id){
        return  cartutil.updateRemoveQuantity(user,id);
    }
    public String changeAddress(Long addressId ,User user ) throws CustomException {
        return  cartutil.changeAddress(addressId ,user.getId());
    }
}
