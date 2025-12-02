package com.condorcoders.postalnavidenabackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Postal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public String slug;
    @Column(name = "image_key")
    public String imageKey;
    @Column(name = "from_name")
    public String fromName;
    @Column(name = "to_name")
    public String toName;
    public String message;

    @PrePersist
    public void generateSlug() {
       this.slug = UUID.randomUUID().toString().substring(0, 8);
    }
}
