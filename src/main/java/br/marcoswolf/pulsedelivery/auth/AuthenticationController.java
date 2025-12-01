package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.model.User;
import br.marcoswolf.pulsedelivery.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserRepository repository;

    public AuthenticationController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(new LoginResponse("Token"));
    }
}
