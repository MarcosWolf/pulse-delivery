package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "OrderUpdateDTO", description = "DTO used to update order status")
public record OrderUpdateDTO(
        @NotNull(message = "Status is required")
        @Schema(description = "Order status", example = "COMPLETED")
        OrderStatus status
) {}