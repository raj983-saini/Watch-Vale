package com.example.Titan.controller.watches;

import com.example.Titan.Response.ApiResponseDto;
import com.example.Titan.utils.thirdPartyApi.OnnxImageClassifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "t1/watch")
public class WarchLabel {

    @PostMapping(value = "/tags")
    private ResponseEntity<ApiResponseDto> response(@RequestParam MultipartFile file) throws Exception {
        List<String> tags = new OnnxImageClassifier().classify(
                                        "src/main/resources/model/alar.jpg",
                "src/main/resources/model/resnet50-v1-7.onnx",
                "src/main/resources/model/label.json"
        );
        System.out.println(tags);
return ResponseEntity.ok(ApiResponseDto.success(tags));
    }
}
