package br.marcoswolf.pulsedelivery.dto.orderItem;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "OrderItemDTO", description = "DTO representing an item in an order")
public record OrderItemDTO(
        @Schema(description = "Order item ID", example = "1")
        Long id,

        @Schema(description = "Product ID", example = "5")
        Long productId,

        @Schema(description = "Quantity of the product", example = "2")
        Integer quantity
) {}
