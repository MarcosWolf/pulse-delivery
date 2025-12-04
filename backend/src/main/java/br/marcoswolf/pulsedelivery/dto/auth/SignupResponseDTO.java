package br.marcoswolf.pulsedelivery.dto.auth;

public record SignupResponseDTO(
        Long id,
        String email,
        String token
) {}
