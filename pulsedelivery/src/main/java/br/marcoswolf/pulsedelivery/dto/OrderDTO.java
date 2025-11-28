package br.marcoswolf.pulsedelivery.dto;

import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        String customerName,
        String address,
        String status,
        LocalDateTime createdAt
) {}
