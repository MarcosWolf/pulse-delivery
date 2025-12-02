package br.marcoswolf.pulsedelivery.dto.auth;

import br.marcoswolf.pulsedelivery.model.Role;

public record RegisterRequestDTO (
   String email,
   String password,
   Role role
) {}
