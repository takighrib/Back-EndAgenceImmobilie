package org.example.backendagenceimmobilier.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = {"https://hajrimmo.tn", "https://www.hajrimmo.tn"})
@Slf4j
public class ImageUploadController {

    // ✅ Chemin relatif depuis le dossier backend
    @Value("${upload.path:uploads/images}")
    private String uploadPathString;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        this.uploadPath = Paths.get(uploadPathString).toAbsolutePath().normalize();

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("✅ Dossier d'upload créé: {}", uploadPath);
            }
        } catch (IOException e) {
            log.error("❌ ERREUR: Impossible de créer le dossier: {}", uploadPath, e);
            throw new RuntimeException("Impossible de créer le dossier: " + uploadPath, e);
        }
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Le fichier est vide"));
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Le fichier doit être une image"));
            }

            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "L'image ne doit pas dépasser 10MB"));
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String filename = UUID.randomUUID().toString() + extension;

            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ✅ URL COMPLÈTE pointant vers Spring Boot
            String imageUrl = "/uploads/images/" + filename;

            log.info("════════════════════════════════════════════════════");
            log.info("📤 IMAGE UPLOADÉE AVEC SUCCÈS");
            log.info("════════════════════════════════════════════════════");
            log.info("Nom original: {}", originalFilename);
            log.info("Nom généré: {}", filename);
            log.info("Chemin physique: {}", filePath.toAbsolutePath());
            log.info("URL d'accès: {}", imageUrl);
            log.info("Taille: {} octets", Files.size(filePath));
            log.info("════════════════════════════════════════════════════");

            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);  // ✅ URL complète !
            response.put("filename", filename);
            response.put("originalName", originalFilename);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            log.error("❌ Erreur lors de l'upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur: " + e.getMessage()));
        }
    }

    @DeleteMapping("/image")
    public ResponseEntity<?> deleteImage(@RequestParam("filename") String filename) {
        try {
            if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Nom de fichier invalide"));
            }

            Path filePath = uploadPath.resolve(filename);
            boolean deleted = Files.deleteIfExists(filePath);

            if (deleted) {
                log.info("✅ Image supprimée: {}", filename);
                return ResponseEntity.ok(Map.of("message", "Image supprimée"));
            } else {
                log.warn("⚠️ Image non trouvée: {}", filename);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Image non trouvée"));
            }
        } catch (IOException e) {
            log.error("❌ Erreur lors de la suppression", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur: " + e.getMessage()));
        }
    }
}
