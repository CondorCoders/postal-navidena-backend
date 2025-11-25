package com.condorcoders.postalnavidenabackend.service;

import com.condorcoders.postalnavidenabackend.model.Postal;
import com.condorcoders.postalnavidenabackend.repository.PostalRepository;
import org.springframework.stereotype.Service;

@Service
public class PostalService {
    private final PostalRepository postalRepository;

    public PostalService(PostalRepository postalRepository) {
        this.postalRepository = postalRepository;
    }

    public Postal createPostal(Postal postal) {
        return postalRepository.save(postal);
    }

    public Postal findBySlug(String slug) {
        return postalRepository.findPostalBySlug(slug);
    }
}
