package br.marcoswolf.pulsedelivery.auth;

import br.marcoswolf.pulsedelivery.dto.auth.SignupRequestDTO;
import br.marcoswolf.pulsedelivery.dto.auth.SignupResponseDTO;
import br.marcoswolf.pulsedelivery.dto.user.UserInfoDTO;
import br.marcoswolf.pulsedelivery.model.User;
import br.marcoswolf.pulsedelivery.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignupRequestDTO requestDTO) {
        UserInfoDTO user = authService.signup(requestDTO);
        String token = jwtService.generateToken(user.id(), user.email(), user.role());
        SignupResponseDTO response = new SignupResponseDTO(
                user.id(),
                user.email(),
                user.role(),
                token
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserInfoDTO> getProfileInfo(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "");
        String email;
        try {
            email = jwtService.extractEmail(token);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        User user = repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        UserInfoDTO dto = new UserInfoDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(dto);
    }
}
