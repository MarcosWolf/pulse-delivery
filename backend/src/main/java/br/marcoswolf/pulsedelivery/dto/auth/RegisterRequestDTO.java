package br.marcoswolf.pulsedelivery.dto.auth;

import br.marcoswolf.pulsedelivery.model.Role;

public record RegisterRequestDTO (
   String name,
   String email,
   String password,
   Role role
) {}
