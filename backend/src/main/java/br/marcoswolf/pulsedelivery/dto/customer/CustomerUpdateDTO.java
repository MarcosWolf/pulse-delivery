package br.marcoswolf.pulsedelivery.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CustomerUpdateDTO", description = "DTO used to update basic customer information")
public record CustomerUpdateDTO(
        @Schema(description = "Customer ID", example = "1")
        Long id,

        @Schema(description = "Customer phone", example = "13912345678")
        String phone
) {}