package com.condorcoders.postalnavidenabackend.controller;

import com.condorcoders.postalnavidenabackend.dto.CreatePostalMultipart;
import com.condorcoders.postalnavidenabackend.dto.CreatePostalRequest;
import com.condorcoders.postalnavidenabackend.dto.CreatePostalResponse;
import com.condorcoders.postalnavidenabackend.dto.PostalOutput;
import com.condorcoders.postalnavidenabackend.model.Postal;
import com.condorcoders.postalnavidenabackend.repository.PostalRepository;
import com.condorcoders.postalnavidenabackend.service.PostalService;
import com.condorcoders.postalnavidenabackend.service.S3Service;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/postal")
public class PostalController {
    private final PostalService postalService;
    private final S3Service s3Service;

    public PostalController(PostalService postalService, S3Service s3Service) {
        this.postalService = postalService;
        this.s3Service = s3Service;
    }

    @GetMapping("/{slug}")
    public PostalOutput findPostalBySlug(@PathVariable String slug) {
        return postalService.findBySlug(slug);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreatePostalResponse createPostal(@ModelAttribute CreatePostalMultipart dto) {
        CreatePostalRequest request = new CreatePostalRequest(
                dto.getFromName(),
                dto.getToName(),
                dto.getMessage(),
                dto.getTheme(),
                dto.getStamp(),
                dto.getBackgroundTheme()
        );
        return postalService.createPostal(request, dto.getFile());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        return s3Service.uploadImage(file);
    }
}
