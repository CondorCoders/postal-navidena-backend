package com.condorcoders.postalnavidenabackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {
    private final S3Client s3;
    private final S3Presigner presigner;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Client s3Client, S3Presigner presigner) {
        this.s3 = s3Client;
        this.presigner = presigner;
    }

    public String uploadImage(MultipartFile file) {
        validateImage(file);

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key = UUID.randomUUID() + "." + extension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        try {
            s3.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        return key;
    }

    public String generatePresignedUrl(String key, Duration duration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(duration)
                .build();

        return presigner.presignGetObject(presignRequest).url().toString();
    }



    private boolean isValidImgExtension(String extension) {
        return "png".equalsIgnoreCase(extension) ||
                "jpg".equalsIgnoreCase(extension) ||
                "jpeg".equalsIgnoreCase(extension) ||
                "gif".equalsIgnoreCase(extension);
    }

    private boolean isImageContentType(String contentType) {
        return "image/png".equalsIgnoreCase(contentType) ||
                "image/jpg".equalsIgnoreCase(contentType) ||
                "image/jpeg".equalsIgnoreCase(contentType) ||
                "image/gif".equalsIgnoreCase(contentType);
    }

    private void validateImage(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String contentType = file.getContentType();

        if (!isValidImgExtension(extension) || !isImageContentType(contentType)) {
            throw new RuntimeException("Unsupported image format: png, jpg, jpeg, gif only");
        }
    }

    }







