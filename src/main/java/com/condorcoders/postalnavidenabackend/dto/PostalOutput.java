package com.condorcoders.postalnavidenabackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostalOutput {
    private Long id;
    private String slug;
    private String fromName;
    private String toName;
    private String message;
    private String imageUrl;
    private String theme;
    private String stamp;
    private String backgroundTheme;
}