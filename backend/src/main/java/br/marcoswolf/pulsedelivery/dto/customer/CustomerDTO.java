package br.marcoswolf.pulsedelivery.dto.customer;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Customer", description = "Represents a customer")
public record CustomerDTO(
        @Schema(description = "Customer ID", example = "1")
        Long id,

        @Schema(description = "Customer phone", example = "13912345678")
        String phone,

        @NotNull(message = "Address is required")
        @Valid
        @Schema(description = "Customer address")
        AddressDTO address
) {}
