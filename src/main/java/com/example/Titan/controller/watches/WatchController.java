package com.example.Titan.controller.watches;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.dtos.watch.WatchesDto;
import com.example.Titan.externalservice.image.ImageTagService;
import com.example.Titan.service.watches.WatchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "t1/watch")
public class WatchController {

    private final WatchService watchService;
    private final ImageTagService imageTagService;


    public WatchController(WatchService watchService, ImageTagService imageTagService) {
        this.watchService = watchService;
        this.imageTagService = imageTagService;
    }
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponseDto> creatWatch(
            @RequestBody WatchesDto watchesDto
    ) throws Exception {
        return ResponseEntity.ok(ApiResponseDto.success(watchService.createWatch( watchesDto)));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponseDto> getWatchByName(@RequestParam(required = false,defaultValue = "") String name) {
        return ResponseEntity.ok(ApiResponseDto.success(watchService.getWatchByName(name)));
    }
    @GetMapping(value = "all")
    public ResponseEntity<ApiResponseDto> getWatches(){
        return ResponseEntity.ok(ApiResponseDto.success(watchService.getAll()));
    }


}
