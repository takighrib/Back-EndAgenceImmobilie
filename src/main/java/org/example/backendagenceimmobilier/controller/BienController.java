package org.example.backendagenceimmobilier.controller;


import jakarta.validation.Valid;
import org.example.backendagenceimmobilier.model.BienImmobilier;
import org.example.backendagenceimmobilier.model.TypeTransaction;
import org.example.backendagenceimmobilier.model.Villa;
import org.example.backendagenceimmobilier.service.BienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biens")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://hajrimmo.tn", "https://www.hajrimmo.tn"})
public class BienController {

    private final BienService bienService;

    @GetMapping
    public ResponseEntity<List<BienImmobilier>> getAllBiens() {
        return ResponseEntity.ok(bienService.getBiensDisponibles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BienImmobilier> getBienById(@PathVariable Long id) {
        return bienService.getBienById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/featured")
    public ResponseEntity<List<BienImmobilier>> getBiensMisEnAvant() {
        return ResponseEntity.ok(bienService.getBiensMisEnAvant());
    }

    @GetMapping("/recents")
    public ResponseEntity<List<BienImmobilier>> getBiensRecents() {
        return ResponseEntity.ok(bienService.getBiensRecents());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BienImmobilier>> rechercherBiens(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @RequestParam(required = false) Double surfaceMin,
            @RequestParam(required = false) String typeTransaction
    ) {
        TypeTransaction transaction = null;
        if (typeTransaction != null) {
            transaction = TypeTransaction.valueOf(typeTransaction.toUpperCase());
        }

        List<BienImmobilier> biens = bienService.rechercherBiens(
                ville, prixMin, prixMax, surfaceMin, transaction
        );
        return ResponseEntity.ok(biens);
    }

    @PostMapping
    public ResponseEntity<BienImmobilier> createBien(@Valid @RequestBody BienImmobilier bien) {
        System.out.println("═══════════════════════════════════════");
        System.out.println("📥 Réception du bien à créer");
        System.out.println("Type: " + bien.getClass().getSimpleName());
        System.out.println("Titre: " + bien.getTitre());
        System.out.println("Nombre d'images reçues: " + (bien.getImages() != null ? bien.getImages().size() : 0));

        if (bien.getImages() != null) {
            bien.getImages().forEach(img ->
                    System.out.println("  - Image URL: " + img.getUrlImage())
            );
        }

        BienImmobilier saved = bienService.saveBien(bien);

        System.out.println("✅ Bien créé avec ID: " + saved.getId());
        System.out.println("   Images sauvegardées: " + saved.getImages().size());
        System.out.println("═══════════════════════════════════════");

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BienImmobilier> updateBien(
            @PathVariable Long id,
            @RequestBody BienImmobilier bien
    ) {
        System.out.println("Type reçu: " + bien.getClass().getSimpleName());
        System.out.println("Jardin: " + (bien instanceof Villa ? ((Villa)bien).getJardin() : "N/A"));
        return bienService.getBienById(id)
                .map(existing -> {
                    bien.setId(id);
                    return ResponseEntity.ok(bienService.saveBien(bien));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBien(@PathVariable Long id) {
        bienService.deleteBien(id);
        return ResponseEntity.ok().build();
    }
}
