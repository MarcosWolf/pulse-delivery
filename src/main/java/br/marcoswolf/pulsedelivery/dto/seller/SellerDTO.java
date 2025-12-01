package br.marcoswolf.pulsedelivery.dto.seller;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;

public record SellerDTO (
    Long id,
    String name,
    String document,
    String phone,
    String email,
    AddressDTO address
){ }
