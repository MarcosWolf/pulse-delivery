package br.marcoswolf.pulsedelivery.dto.user;

import br.marcoswolf.pulsedelivery.model.Role;

public record UserInfoDTO (
        String name,
        String email,
        Role role
) {}
