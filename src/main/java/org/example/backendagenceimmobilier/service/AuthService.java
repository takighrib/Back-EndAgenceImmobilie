package org.example.backendagenceimmobilier.service;

import org.example.backendagenceimmobilier.dto.LoginRequest;
import org.example.backendagenceimmobilier.dto.LoginResponse;
import org.example.backendagenceimmobilier.model.Admin;
import org.example.backendagenceimmobilier.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final AdminRepository adminRepository;

    /**
     * Authentifier un administrateur
     */
    public Optional<LoginResponse> login(LoginRequest request) {
        Optional<Admin> admin = adminRepository.findByUsernameAndPassword(
                request.getUsername(),
                request.getPassword()
        );

        return admin
                .filter(Admin::isActif)
                .map(a -> new LoginResponse(
                        a.getId(),
                        a.getUsername(),
                        a.getNom(),
                        a.getPrenom(),
                        a.getEmail()
                ));
    }

    /**
     * Créer un administrateur par défaut si aucun n'existe
     */
    public void createDefaultAdminIfNotExists() {
        if (adminRepository.count() == 0) {
            Admin defaultAdmin = new Admin();
            defaultAdmin.setUsername("fedy");
            defaultAdmin.setPassword("fedy@hajri");
            defaultAdmin.setNom("Hajri");
            defaultAdmin.setPrenom("Fedy");
            defaultAdmin.setEmail("fedy@hajriimmo.com");
            defaultAdmin.setActif(true);
            adminRepository.save(defaultAdmin);

        } else {
            log.info("ℹ️  Un administrateur existe déjà dans la base de données");
        }
    }

    /**
     * Valider un token simple
     */
    public boolean validateToken(String token) {
        try {
            String decoded = new String(java.util.Base64.getDecoder().decode(token));
            String[] parts = decoded.split(":");
            if (parts.length == 3) {
                Long id = Long.parseLong(parts[0]);
                String username = parts[1];
                Long timestamp = Long.parseLong(parts[2]);

                // Token valide pour 24 heures
                long tokenAge = System.currentTimeMillis() - timestamp;
                if (tokenAge > 24 * 60 * 60 * 1000) {
                    return false;
                }

                // Vérifier que l'admin existe toujours
                return adminRepository.findById(id)
                        .map(admin -> admin.getUsername().equals(username) && admin.isActif())
                        .orElse(false);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}