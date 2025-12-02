package com.condorcoders.postalnavidenabackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostalRequest {

    @NotBlank
    private String fromName;

    @NotBlank
    private String toName;

    @NotBlank
    private String message;
}
