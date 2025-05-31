package com.example.Titan.service.watches;

import com.example.Titan.dtos.watch.WatchesDto;
import com.example.Titan.entity.watches.Function;
import com.example.Titan.entity.watches.WatchImage;
import com.example.Titan.entity.watches.Watches;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchService {
    private final WatchServiceImpl watchService;

    public WatchService( WatchServiceImpl watchService) {
        this.watchService = watchService;
    }
    public String createWatch(WatchesDto watchesDto){
        Watches watches = new Watches();
        watches.setBrand(watchesDto.getBrand());
        watches.setBluetooth(watchesDto.isBluetooth());
        watches.setName(watchesDto.getName());
        watches.setDescription(watchesDto.getDescription());
        watches.setBatteryLife(watchesDto.getBatteryLife());
        watches.setDiscount(watchesDto.getDiscount());
        watches.setAvailabilityStatus(watchesDto.isIsavailabile());
        watches.setDialColor(watchesDto.getDialColor());
        watches.setDialSize(watchesDto.getDialSize());
        watches.setDialShape(watchesDto.getDialShape());
        watches.setMaterial(watchesDto.getMaterial());
        watches.setPrice(watchesDto.getPrice());
        watches.setRating(watchesDto.getRating());
        watches.setScreenSize(watchesDto.getScreenSize());
        watches.setStrapColor(watchesDto.getStrapColor());
        watches.setStrapMaterial(watchesDto.getStrapMaterial());
        watches.setStrapSize(watchesDto.getStrapSize());
        watches.setType(watchesDto.getType());
        watches.setWarrantyPeriod(watchesDto.getWarrantyPeriod());
        watches.setStrapShape(watchesDto.getStrapShape());

        // Set Images
        List<WatchImage> images = watchesDto.getImage().stream()
                .map(url -> new WatchImage(url,watches))
                .collect(Collectors.toList());
        watches.setImage(images);

        // Set Funtion
        List<Function> funtion = watchesDto.getFunctions().stream()
                .map(name -> new Function(name,watches))
                .collect(Collectors.toList());
        watches.setFunctions(funtion);
        watchService.createWatches(watches);
        return  "Watch Create Succusfully";
    }
    public WatchesDto getWatchByName(String name) {
        return  watchService.getWatch(name);
    }
public List<Watches> getAll(){return  watchService.getall();}
}
