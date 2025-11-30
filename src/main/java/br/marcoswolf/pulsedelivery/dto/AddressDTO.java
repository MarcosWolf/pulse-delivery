package br.marcoswolf.pulsedelivery.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Address", description = "Represents a customer address")
public record AddressDTO (
        @Schema(description = "Street name", example = "Avenue Vinicios")
        String street,

        @Schema(description = "Street number", example = "123")
        String number,

        @Schema(description = "Address complement", example = "Apt 101")
        String complement,

        @Schema(description = "Neighborhood", example = "Centro")
        String neighborhood,

        @Schema(description = "City name", example = "São Paulo")
        String city,

        @Schema(description = "State name", example = "São Paulo")
        String state,

        @Schema(description = "Postal code", example = "11111-123")
        String postalCode,

        @Schema(description = "Country", example = "Brazil")
        String country
) {}
