package com.condorcoders.postalnavidenabackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class CreatePostalMultipart {
    @NotBlank
    private String fromName;

    @NotBlank
    private String toName;

    @NotBlank
    private String message;

    @NotBlank
    private String theme;

    @NotBlank
    private String stamp;

    @NotBlank
    private String backgroundTheme;

    @NotNull
    private MultipartFile file;

}
