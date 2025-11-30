package br.marcoswolf.pulsedelivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Category", description = "Represents a product category")
public record CategoryDTO (
        @Schema(description = "Category ID", example = "1")
        Long id,

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        @Schema(description = "Category name", example = "Snacks")
        String name,

        @NotNull(message = "Active status is required")
        @Schema(description = "Whether the category is active", example = "true")
        Boolean active
) {}
