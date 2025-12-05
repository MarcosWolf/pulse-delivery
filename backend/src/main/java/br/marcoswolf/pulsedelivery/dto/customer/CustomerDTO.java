package br.marcoswolf.pulsedelivery.dto.customer;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;
import br.marcoswolf.pulsedelivery.dto.user.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Customer", description = "Represents a customer")
public record CustomerDTO(
        @Valid
        @Schema(description = "Customer User")
        UserDTO user,

        @Schema(description = "Customer phone", example = "13912345678")
        String phone,

        @NotNull(message = "Address is required")
        @Valid
        @Schema(description = "Customer address")
        AddressDTO address
) {}
