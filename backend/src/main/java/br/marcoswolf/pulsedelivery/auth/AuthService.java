package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.dto.auth.SignupRequestDTO;
import br.marcoswolf.pulsedelivery.dto.user.UserInfoDTO;
import br.marcoswolf.pulsedelivery.model.Customer;
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

    public UserInfoDTO signup(SignupRequestDTO requestDTO) {
        if (repository.existsByEmail(requestDTO.email())) {
            throw new RuntimeException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setName(requestDTO.name());
        customer.setEmail(requestDTO.email());
        customer.setPassword(passwordEncoder.encode(requestDTO.password()));
        customer.setRole(requestDTO.role());

        User savedUser = repository.save(customer);

        return new UserInfoDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }
}
