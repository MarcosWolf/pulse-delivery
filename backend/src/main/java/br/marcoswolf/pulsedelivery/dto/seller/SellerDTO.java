package br.marcoswolf.pulsedelivery.dto.seller;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.user.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Seller", description = "Represents a seller")
public record SellerDTO (
        @Valid
        @Schema(description = "Seller User")
        UserDTO user,

        @NotNull(message = "Document is required")
        @Valid
        @Schema(description = "Seller document", example = "12345678000100")
        String document,

        @NotNull(message = "Phone is required")
        @Valid
        @Schema(description = "Seller phone", example = "13912345678")
        String phone,

        @NotNull(message = "Address is required")
        @Valid
        @Schema(description = "Seller address")
        AddressDTO address
){ }
