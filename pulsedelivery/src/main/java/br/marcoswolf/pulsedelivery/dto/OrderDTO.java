package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;

import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        String customerName,
        String address,
        OrderStatus status,
        LocalDateTime createdAt
) {}
