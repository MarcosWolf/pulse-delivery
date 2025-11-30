package br.marcoswolf.pulsedelivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(name = "OrderItemDTO", description = "DTO representing an item in an order")
public record OrderItemDTO(
        @Schema(description = "Order item ID", example = "1")
        Long id,

        @Schema(description = "Product name", example = "Cheeseburger")
        String productName,

        @Schema(description = "Quantity of the product", example = "2")
        Integer quantity,

        @Schema(description = "Price per unit", example = "15.99")
        BigDecimal price
) {}
