package br.marcoswolf.pulsedelivery.dto.auth;

import br.marcoswolf.pulsedelivery.model.Role;

public record SignupResponseDTO(
        Long id,
        String email,
        Role  role,
        String token
) {}
