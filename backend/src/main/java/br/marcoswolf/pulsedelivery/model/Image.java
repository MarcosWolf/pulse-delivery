package br.marcoswolf.pulsedelivery.model;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Image {
    private String imageUrl;
    private LocalDateTime uploadedAt;

    public Image() {
        this.uploadedAt = LocalDateTime.now();
    }

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
        this.uploadedAt = LocalDateTime.now();
    }
}