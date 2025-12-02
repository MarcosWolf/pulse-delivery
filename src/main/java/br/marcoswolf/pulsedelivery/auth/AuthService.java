package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.dto.auth.RegisterRequestDTO;
import br.marcoswolf.pulsedelivery.model.User;
import br.marcoswolf.pulsedelivery.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequestDTO requestDTO) {
        if (repository.existsByEmail(requestDTO.email())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(requestDTO.email());
        user.setEmail(requestDTO.email());
        user.setRole(requestDTO.role());

        return repository.save(user);
    }
}
