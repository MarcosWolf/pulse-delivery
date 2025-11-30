package br.marcoswolf.pulsedelivery.dto;

public record SellerDTO (
    Long id,
    String name,
    String document,
    String phone,
    String email,
    AddressDTO address
){ }
