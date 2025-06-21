package com.example.Titan.externalservice.image;


import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;
import java.util.Map;

@Service
public class ImageTagService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<String> getTagsFromImage(MultipartFile image) throws Exception {
        String url = "http://localhost:5000/predict";

        // Wrap image in resource
        ByteArrayResource imageResource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        };

        // Prepare body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", imageResource);

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Prepare request
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send request
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        return (List<String>) response.getBody().get("tags");
    }
}
