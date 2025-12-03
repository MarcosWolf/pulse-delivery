package br.marcoswolf.pulsedelivery.dto.seller;

import br.marcoswolf.pulsedelivery.dto.address.AddressDTO;

public record SellerDTO (
    Long id,
    String document,
    String phone,
    AddressDTO address
){ }
