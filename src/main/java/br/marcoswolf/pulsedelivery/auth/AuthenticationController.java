package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.dto.auth.RegisterRequestDTO;
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
    private final AuthService authService;

    public AuthenticationController(UserRepository repository, AuthService authService) {
        this.repository = repository;
        this.authService = authService;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO requestDTO) {
        User user = authService.register(requestDTO);
        return ResponseEntity.ok("User created with id: " + user.getId());
    }
}
