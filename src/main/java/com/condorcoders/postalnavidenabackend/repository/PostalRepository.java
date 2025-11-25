package com.condorcoders.postalnavidenabackend.repository;

import com.condorcoders.postalnavidenabackend.model.Postal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalRepository extends JpaRepository<Postal, Long> {

    public Postal findPostalBySlug(String slug);

}
