package com.example.Titan.service.auth;

import com.example.Titan.daos.auth.AddressDao;
import com.example.Titan.dtos.auth.AddressDto;
import com.example.Titan.entity.user.Address;
import com.example.Titan.entity.user.User;
import com.example.Titan.expections.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressService {
private final AddressDao addressDao;

    public AddressService(AddressDao addressDao) {
        this.addressDao = addressDao;
    }
    public Address createAddress(AddressDto addressDto , User user){
        log.info("Address Data {}",addressDto);
     Address address = new Address();
     address.setFullName(addressDto.getFullName());
     address.setPhoneNumber(addressDto.getMobile());
     address.setAddressLine1(addressDto.getStreetAddress());
     address.setPostalCode(addressDto.getPostalCode());
     address.setCity(addressDto.getCity());
     address.setState(addressDto.getState());
     address.setCountry(addressDto.getCountry());
     address.setUserId(user.getId());

  return  addressDao.saveAndFlush(address);
    }
    public Address getById(Long id) throws CustomException {
        Optional<Address> address = addressDao.findById(id);
        if (!address.isPresent()){
            throw new CustomException("address Not Found");
        }
        return address.get();
    }
    public List<Address> getAddressByUser(User user){
     return  addressDao.findByUserId(user.getId());
    }

}
