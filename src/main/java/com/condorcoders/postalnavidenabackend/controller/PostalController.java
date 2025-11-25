package com.condorcoders.postalnavidenabackend.controller;

import com.condorcoders.postalnavidenabackend.model.Postal;
import com.condorcoders.postalnavidenabackend.service.PostalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postal")
public class PostalController {
    private final PostalService postalService;

    public PostalController(PostalService postalService) {
        this.postalService = postalService;
    }

    @GetMapping("/{slug}")
    public Postal findPostalBySlug(@PathVariable String slug) {
        return postalService.findBySlug(slug);
    }

    @PostMapping
    public Postal createPostal(@RequestBody Postal postal) {
        return postalService.createPostal(postal);
    }
}
