package br.marcoswolf.pulsedelivery.dto.user;

import br.marcoswolf.pulsedelivery.model.Role;

public record UserDTO(
   Long id,
   String name,
   String email,
   String password,
   Role role
) {}
