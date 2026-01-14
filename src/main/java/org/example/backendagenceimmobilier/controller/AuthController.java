package org.example.backendagenceimmobilier.controller;

import org.example.backendagenceimmobilier.dto.LoginRequest;
import org.example.backendagenceimmobilier.dto.LoginResponse;
import org.example.backendagenceimmobilier.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        // Retirer "Bearer " du token si présent
        String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        boolean isValid = authService.validateToken(actualToken);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/init")
    public ResponseEntity<String> initDefaultAdmin() {
        authService.createDefaultAdminIfNotExists();
        return ResponseEntity.ok("Admin par défaut créé si nécessaire");
    }
}