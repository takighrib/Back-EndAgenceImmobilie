package org.example.backendagenceimmobilier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String username;
    private String nom;
    private String prenom;
    private String email;
    private String token; // Token simple pour la session

    public LoginResponse(Long id, String username, String nom, String prenom, String email) {
        this.id = id;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.token = generateToken(id, username);
    }

    private String generateToken(Long id, String username) {
        // Token simple: base64(id:username:timestamp)
        String tokenData = id + ":" + username + ":" + System.currentTimeMillis();
        return java.util.Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
}