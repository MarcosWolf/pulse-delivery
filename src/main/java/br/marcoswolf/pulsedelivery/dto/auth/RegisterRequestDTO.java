package br.marcoswolf.pulsedelivery.dto.auth;

public record RegisterRequestDTO (
   String email,
   String password,
   String role
) {}
