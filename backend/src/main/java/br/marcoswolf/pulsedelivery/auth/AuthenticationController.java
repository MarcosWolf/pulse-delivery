package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.dto.auth.RegisterRequestDTO;
import br.marcoswolf.pulsedelivery.dto.user.UserInfoDTO;
import br.marcoswolf.pulsedelivery.model.User;
import br.marcoswolf.pulsedelivery.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserRepository repository;
    private final AuthService authService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(
            UserRepository repository, AuthService authService,
            JwtService jwtService, PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.authService = authService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO requestDTO) {
        User user = authService.register(requestDTO);
        return ResponseEntity.ok("User created with id: " + user.getId());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfoDTO> getProfileInfo(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractEmail(token);
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserInfoDTO dto = new UserInfoDTO(user.getName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(dto);
    }
}
