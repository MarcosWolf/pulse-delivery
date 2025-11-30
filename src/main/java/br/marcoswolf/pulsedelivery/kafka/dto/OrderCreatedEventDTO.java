package br.marcoswolf.pulsedelivery.kafka.dto;

import java.time.LocalDateTime;

public record OrderCreatedEventDTO(
        Long orderId,
        Long customerId,
        String customerName,
        String deliveryAddress,
        LocalDateTime createdAt
) {}