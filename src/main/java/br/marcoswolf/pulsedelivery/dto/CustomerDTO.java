package br.marcoswolf.pulsedelivery.dto;

import br.marcoswolf.pulsedelivery.model.Address;

public record CustomerDTO(
        Long id,
        String name,
        String email,
        AddressDTO address
) {}
