package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;

import java.time.LocalDateTime;

public record OrderDTO(
        Long id,
        CustomerDTO customer,
        OrderStatus status,
        LocalDateTime createdAt
) {}