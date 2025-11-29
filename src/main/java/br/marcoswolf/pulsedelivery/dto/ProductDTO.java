package br.marcoswolf.pulsedelivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductDTO (
        Long id,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name,

        @Size(max = 500, message = "Description must be at most 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock must be zero or positive")
        Integer stock,

        @NotNull(message = "Category is required")
        @Valid
        CategoryDTO category,

        @NotNull(message = "Active status is required")
        Boolean active
) {}