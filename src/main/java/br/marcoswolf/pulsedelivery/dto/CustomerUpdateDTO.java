package br.marcoswolf.pulsedelivery.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CustomerUpdateDTO", description = "DTO used to update basic customer information")
public record CustomerUpdateDTO(
        @Schema(description = "Customer ID", example = "1")
        Long id,

        @NotBlank(message = "Name is required")
        @Schema(description = "Customer name", example = "John Doe")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Schema(description = "Customer email", example = "john.doe@example.com")
        String email
) {}