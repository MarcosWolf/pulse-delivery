package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,

        @NotNull(message = "Customer is required")
        @Valid
        CustomerDTO customer,

        @NotNull(message = "Status is required")
        OrderStatus status,

        LocalDateTime createdAt,

        @NotNull(message = "Order items are required")
        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemDTO> orderItems
) {}