package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        CustomerDTO customer,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItemDTO> orderItems
) {}