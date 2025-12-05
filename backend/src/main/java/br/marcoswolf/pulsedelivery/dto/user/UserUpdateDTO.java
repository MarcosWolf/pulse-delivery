package br.marcoswolf.pulsedelivery.dto.user;

import br.marcoswolf.pulsedelivery.model.Role;

public record UserUpdateDTO (
        String name,
        String email,
        String password,
        Role role
) {}
