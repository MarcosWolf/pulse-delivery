package br.marcoswolf.pulsedelivery.dto.customer;

import br.marcoswolf.pulsedelivery.dto.user.UserUpdateDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CustomerUpdateDTO", description = "DTO used to update basic customer information")
public record CustomerUpdateDTO(
        @Valid
        @Schema(description = "Customer")
        UserUpdateDTO user,

        @Schema(description = "Customer phone", example = "13912345678")
        String phone
) {}