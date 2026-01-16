package org.example.backendagenceimmobilier.service;

import org.example.backendagenceimmobilier.model.BienImmobilier;
import org.example.backendagenceimmobilier.model.ImageBien;
import org.example.backendagenceimmobilier.model.StatutBien;
import org.example.backendagenceimmobilier.model.TypeTransaction;
import org.example.backendagenceimmobilier.repository.BienImmobilierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (bien.getImages() != null) {
            for (ImageBien img : bien.getImages()) {
                img.setBien(bien); // Associer chaque image au bien parent
            }
        }
        return bienRepository.save(bien);
    }

    public void deleteBien(Long id) {
        bienRepository.deleteById(id);
    }
}
