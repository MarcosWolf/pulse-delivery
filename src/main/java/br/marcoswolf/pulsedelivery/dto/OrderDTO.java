package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "OrderDTO", description = "DTO representing a customer order")
public record OrderDTO(
        @Schema(description = "Order ID", example = "1001")
        Long id,

        @NotNull(message = "Customer is required")
        @Valid
        @Schema(description = "Customer information")
        CustomerDTO customer,

        @NotNull(message = "Status is required")
        @Schema(description = "Order status", example = "CREATED")
        OrderStatus status,

        @Schema(description = "Order creation date and time", example = "2025-11-29T12:34:56")
        LocalDateTime createdAt,

        @NotNull(message = "Order items are required")
        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        @Schema(description = "List of order items")
        List<OrderItemDTO> orderItems
) {}