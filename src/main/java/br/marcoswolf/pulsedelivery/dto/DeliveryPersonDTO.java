package br.marcoswolf.pulsedelivery.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "DeliveryPerson", description = "Represents a delivery person")
public record DeliveryPersonDTO(
        @Schema(description = "ID of the delivery person", example = "1")
        Long id,

        @NotBlank(message = "Name is required")
        @Schema(description = "Full name of the delivery person", example = "John Doe")
        String name,

        @NotBlank(message = "Phone is required")
        @Schema(description = "Phone number of the delivery person", example = "+123456789")
        String phone,

        @NotBlank(message = "Document is required")
        @Schema(description = "Document number (e.g., CPF or ID)", example = "123.456.789-00")
        String document,

        @Schema(description = "Indicates if the delivery person is active", example = "true")
        Boolean active,

        @Schema(description = "The date and time when the delivery person was created", example = "2025-11-29T23:45:00")
        LocalDateTime createdAt
) {}