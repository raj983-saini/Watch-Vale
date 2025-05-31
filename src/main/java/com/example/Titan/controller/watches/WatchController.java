package com.example.Titan.controller.watches;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.dtos.watch.WatchesDto;
import com.example.Titan.service.watches.WatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "t1/watch")
public class WatchController {

    private final WatchService watchService;

    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponseDto> creatWatch(@RequestBody WatchesDto watchesDto){
        return  ResponseEntity.ok(ApiResponseDto.success(watchService.createWatch(watchesDto)));
    }
    @GetMapping(value = "/{name}")
    public ResponseEntity<ApiResponseDto> getWatchByName(@PathVariable String name) {
        return ResponseEntity.ok(ApiResponseDto.success(watchService.getWatchByName(name)));
    }
    @GetMapping(value = "all")
    public ResponseEntity<ApiResponseDto> getWatches(){
        return ResponseEntity.ok(ApiResponseDto.success(watchService.getAll()));
    }
}
