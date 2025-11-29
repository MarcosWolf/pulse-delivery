package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateDTO(
        @NotNull(message = "Status is required")
        OrderStatus status
) {}
