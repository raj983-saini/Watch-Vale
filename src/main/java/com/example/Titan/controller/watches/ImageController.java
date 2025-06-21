package com.example.Titan.controller.watches;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.externalservice.image.ImageTagService;
import com.example.Titan.service.watches.WatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/t1/images")
public class ImageController {

    private final ImageTagService imageTagService;
    private final WatchService watchService;

    public ImageController(ImageTagService imageTagService, WatchService watchService) {
        this.imageTagService = imageTagService;
        this.watchService = watchService;
    }

    @PostMapping(value = "/tags", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> getImageTags(@RequestPart("image") MultipartFile image) {
        try {
            List<String> tags = imageTagService.getTagsFromImage(image);
            return ResponseEntity.ok(Map.of("tags", tags));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @PostMapping(value = "/watch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto> getWatchesByTags(@RequestPart("file") MultipartFile image) throws Exception {
        List<String> tags = imageTagService.getTagsFromImage(image);
        tags.forEach(System.out::println);
        return ResponseEntity.ok(ApiResponseDto.success(watchService.getAllByTags(tags)));
    }
}
