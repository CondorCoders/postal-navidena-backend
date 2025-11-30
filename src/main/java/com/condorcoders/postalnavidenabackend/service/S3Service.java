package com.condorcoders.postalnavidenabackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;

@Service
public class S3Service {
    private final S3Client s3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3Service(S3Client s3Client) {
        this.s3 = s3Client;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();

         s3.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

         return file.getOriginalFilename();
    }






}
