package br.marcoswolf.pulsedelivery.dto.seller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "SellerUpdateDTO", description = "DTO used to update basic seller information")
public record SellerUpdateDTO (
        @Schema(description = "Seller ID", example = "1")
        Long id,

        @NotBlank(message = "Document is required")
        @Schema(description = "Seller document", example = "12.345.678/0001-00")
        String document,

        @NotBlank(message = "Phone is required")
        @Schema(description = "Seller phone", example = "13912345678")
        String phone
) {}
