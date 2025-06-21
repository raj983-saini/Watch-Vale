package com.example.Titan.service.watches;

import com.example.Titan.daos.watches.WatchDao;
import com.example.Titan.dtos.watch.WatchesDto;
import com.example.Titan.entity.watches.Function;
import com.example.Titan.entity.watches.WatchImage;
import com.example.Titan.entity.watches.Watches;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchServiceImpl {
    private final WatchDao watchDao;

    public WatchServiceImpl(WatchDao watchDao) {
        this.watchDao = watchDao;
    }
    public Watches createWatches(Watches watches){
        return watchDao.save(watches);
    }
    public List<Watches> getall(){return  watchDao.findAll();}
    public List<Watches> getallByTags(List<String> tags ){return  watchDao.findByTagNames(tags);}
    public List<Watches> getWatch(String name) {
       return watchDao.searchByFirstWord(name);
    }
    private WatchesDto mapToDto(Watches watch) {
        WatchesDto dto = new WatchesDto();
        dto.setBrand(watch.getBrand());
        dto.setBluetooth(watch.isBluetooth());
        dto.setName(watch.getName());
        dto.setDescription(watch.getDescription());
        dto.setBatteryLife(watch.getBatteryLife());
        dto.setDiscount(watch.getDiscount());
        dto.setIsavailabile(watch.isAvailabilityStatus());
        dto.setDialColor(watch.getDialColor());
        dto.setDialSize(watch.getDialSize());
        dto.setDialShape(watch.getDialShape());
        dto.setMaterial(watch.getMaterial());
        dto.setPrice(watch.getPrice());
        dto.setRating(watch.getRating());
        dto.setScreenSize(watch.getScreenSize());
        dto.setStrapColor(watch.getStrapColor());
        dto.setStrapMaterial(watch.getStrapMaterial());
        dto.setStrapSize(watch.getStrapSize());
        dto.setType(watch.getType());
        dto.setWarrantyPeriod(watch.getWarrantyPeriod());
        dto.setStrapShape(watch.getStrapShape());

        // Convert images to a list of URLs
        dto.setImage(watch.getImage().stream()
                .map(WatchImage::getImageUrl)
                .collect(Collectors.toList()));

        // Convert functions to a list of names
        dto.setFunctions(watch.getFunctions().stream()
                .map(Function::getFunctionName)
                .collect(Collectors.toList()));

        return dto;
    }

}
