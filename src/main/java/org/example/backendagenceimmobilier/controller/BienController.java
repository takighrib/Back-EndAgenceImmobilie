package org.example.backendagenceimmobilier.controller;


import jakarta.validation.Valid;
import org.example.backendagenceimmobilier.model.BienImmobilier;
import org.example.backendagenceimmobilier.model.TypeTransaction;
import org.example.backendagenceimmobilier.service.BienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biens")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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

    // Endpoints pour l'administration (à sécuriser plus tard)
    @PostMapping
    public ResponseEntity<BienImmobilier> createBien(@Valid @RequestBody BienImmobilier bien) {
        BienImmobilier saved = bienService.saveBien(bien);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BienImmobilier> updateBien(
            @PathVariable Long id,
            @RequestBody BienImmobilier bien
    ) {
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
