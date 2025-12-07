package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.dto.auth.SignupRequestDTO;
import br.marcoswolf.pulsedelivery.dto.user.UserInfoDTO;
import br.marcoswolf.pulsedelivery.model.Customer;
import br.marcoswolf.pulsedelivery.model.DeliveryPerson;
import br.marcoswolf.pulsedelivery.model.Seller;
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

        User user;

        switch (requestDTO.role()) {
            case CUSTOMER:
                user = new Customer();
                break;

            case SELLER:
                user = new Seller();
                break;

            case DELIVERYPERSON:
                user = new DeliveryPerson();
                break;

            default:
                throw new RuntimeException("Invalid role");
        }

        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        user.setRole(requestDTO.role());
        user.setActive(true);

        User saved = repository.save(user);

        return new UserInfoDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getRole()
        );
    }
}
