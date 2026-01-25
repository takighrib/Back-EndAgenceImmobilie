package org.example.backendagenceimmobilier.service;

import org.example.backendagenceimmobilier.model.BienImmobilier;
import org.example.backendagenceimmobilier.model.StatutBien;
import org.example.backendagenceimmobilier.model.TypeTransaction;
import org.example.backendagenceimmobilier.repository.BienImmobilierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BienService {

    private final BienImmobilierRepository bienRepository;

    public List<BienImmobilier> getAllBiens() {
        return bienRepository.findAll();
    }

    public List<BienImmobilier> getBiensDisponibles() {
        return bienRepository.findByStatut(StatutBien.DISPONIBLE);
    }

    public List<BienImmobilier> getBiensMisEnAvant() {
        return bienRepository.findByMisEnAvantTrueOrderByOrdreAffichageAsc();
    }

    public Optional<BienImmobilier> getBienById(Long id) {
        return bienRepository.findById(id);
    }

    public List<BienImmobilier> rechercherBiens(
            String ville,
            Double prixMin,
            Double prixMax,
            Double surfaceMin,
            TypeTransaction typeTransaction
    ) {
        return bienRepository.rechercheAvancee(ville, prixMin, prixMax, surfaceMin, typeTransaction);
    }

    public List<BienImmobilier> getBiensRecents() {
        return bienRepository.findBiensRecents();
    }

    public List<BienImmobilier> getBiensByVille(String ville) {
        return bienRepository.findByVilleContainingIgnoreCase(ville);
    }

    public BienImmobilier saveBien(BienImmobilier bien) {
        // Initialiser la liste d'images si elle est nulle
        if (bien.getImages() == null) {
            bien.setImages(new ArrayList<>());
        }

        // CRITIQUE : Associer le bien à chaque image AVANT la sauvegarde
        bien.getImages().forEach(img -> {
            img.setBien(bien);  // Établir la relation bidirectionnelle
            System.out.println("🔗 Association image → bien pour URL: " + img.getUrlImage());
        });

        // Sauvegarder le bien (cascade ALL sur les images)
        BienImmobilier saved = bienRepository.save(bien);

        System.out.println("💾 Bien sauvegardé avec " + saved.getImages().size() + " images");

        return saved;
    }

    public void deleteBien(Long id) {
        bienRepository.deleteById(id);
    }
}