package com.condorcoders.postalnavidenabackend.service;

import com.condorcoders.postalnavidenabackend.dto.CreatePostalRequest;
import com.condorcoders.postalnavidenabackend.dto.CreatePostalResponse;
import com.condorcoders.postalnavidenabackend.dto.PostalOutput;
import com.condorcoders.postalnavidenabackend.model.Postal;
import com.condorcoders.postalnavidenabackend.repository.PostalRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@Service
public class PostalService {
    private final S3Service s3;
    private final PostalRepository postalRepository;

    public PostalService(PostalRepository postalRepository, S3Service s3) {
        this.postalRepository = postalRepository;
        this.s3 = s3;
    }

    public CreatePostalResponse createPostal(CreatePostalRequest dto, MultipartFile file) {
        // Subimos la imagen a AWS s3
        String imageKey = s3.uploadImage(file);

        // Map dto → entity
        Postal postal = new Postal();
        postal.setFromName(dto.getFromName());
        postal.setToName(dto.getToName());
        postal.setMessage(dto.getMessage());
        postal.setImageKey(imageKey);

        // Save entity
        Postal savedPostal = postalRepository.save(postal);

        return  new CreatePostalResponse(savedPostal.getSlug());
    }

    public PostalOutput findBySlug(String slug) {
        Postal postalEntity = postalRepository.findPostalBySlug(slug);

        if (postalEntity == null) {
            throw new RuntimeException("Postal not found");
        }

        String imageUrl = s3.generatePresignedUrl(postalEntity.getImageKey(), Duration.ofHours(1));

        if (imageUrl == null) {
            throw new RuntimeException("Image not found");
        }

        return new PostalOutput(
                postalEntity.getId(),
                postalEntity.getSlug(),
                postalEntity.getFromName(),
                postalEntity.getToName(),
                postalEntity.getMessage(),
                imageUrl
        );
    }
}
