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
@CrossOrigin(origins = {"https://hajrimmo.tn", "https://www.hajrimmo.tn"})
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            boolean isValid = authService.validateToken(token);
            return ResponseEntity.ok(isValid);
        }
        return ResponseEntity.ok(false);
    }

    @PostMapping("/init")
    public ResponseEntity<String> initDefaultAdmin() {
        authService.createDefaultAdminIfNotExists();
        return ResponseEntity.ok("Vérification de l'admin effectuée");
    }
}