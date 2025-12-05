package br.marcoswolf.pulsedelivery.dto.seller;

import br.marcoswolf.pulsedelivery.dto.user.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "SellerUpdateDTO", description = "DTO used to update basic seller information")
public record SellerUpdateDTO (
        @Valid
        @Schema(description = "Seller User")
        UserDTO user,

        @NotBlank(message = "Document is required")
        @Schema(description = "Seller document", example = "12345678000100")
        String document,

        @NotBlank(message = "Phone is required")
        @Schema(description = "Seller phone", example = "13912345678")
        String phone
) {}
