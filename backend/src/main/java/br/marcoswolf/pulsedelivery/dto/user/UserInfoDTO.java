package br.marcoswolf.pulsedelivery.dto.user;

import br.marcoswolf.pulsedelivery.model.Role;

public record UserInfoDTO (
    String email,
    Role role
) {}
