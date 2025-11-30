package br.marcoswolf.pulsedelivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(name = "ProductDTO", description = "DTO representing a product")
public record ProductDTO(
        @Schema(description = "Product ID", example = "101")
        Long id,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        @Schema(description = "Product name", example = "Cheeseburger")
        String name,

        @Size(max = 500, message = "Description must be at most 500 characters")
        @Schema(description = "Product description", example = "Delicious cheeseburger with extra cheese")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        @Schema(description = "Product price", example = "15.99")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock must be zero or positive")
        @Schema(description = "Product stock quantity", example = "50")
        Integer stock,

        @NotNull(message = "Category is required")
        @Valid
        @Schema(description = "Category of the product")
        CategoryDTO category,

        @NotNull(message = "Active status is required")
        @Schema(description = "Whether the product is active", example = "true")
        Boolean active
) {}