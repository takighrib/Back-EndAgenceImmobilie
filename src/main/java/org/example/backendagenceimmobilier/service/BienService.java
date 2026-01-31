package org.example.backendagenceimmobilier.service;

import lombok.RequiredArgsConstructor;
import org.example.backendagenceimmobilier.model.*;
import org.example.backendagenceimmobilier.repository.BienImmobilierRepository;
import org.example.backendagenceimmobilier.repository.ImageBienRepository;
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
    private final ImageBienRepository imageBienRepository;

    // ==========================
    // LECTURE
    // ==========================

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

    // ==========================
    // SAUVEGARDE
    // ==========================

    public BienImmobilier saveBien(BienImmobilier bien) {
        if (bien.getId() == null) {
            return createBien(bien);
        }

        Optional<BienImmobilier> existingOpt = bienRepository.findById(bien.getId());

        if (existingOpt.isPresent()) {
            BienImmobilier existingBien = existingOpt.get();

            // Si le type a changé, on supprime l'ancien et on crée un nouveau bien
            if (!existingBien.getClass().equals(bien.getClass())) {
                // Suppression des images liées avant suppression du bien
                imageBienRepository.findAll().stream()
                        .filter(img -> img.getBien().getId().equals(existingBien.getId()))
                        .forEach(imageBienRepository::delete);

                bienRepository.delete(existingBien);

                // Supprimer l'id pour forcer la création
                bien.setId(null);

                // Associer les images au nouveau bien
                if (bien.getImages() != null) {
                    bien.getImages().forEach(img -> img.setBien(bien));
                } else {
                    bien.setImages(new ArrayList<>());
                }

                return bienRepository.save(bien);
            } else {
                // Sinon on fait une mise à jour classique
                return updateBien(bien);
            }
        } else {
            // Bien non trouvé, création classique
            return createBien(bien);
        }
    }

    // ==========================
    // CREATE
    // ==========================

    private BienImmobilier createBien(BienImmobilier bien) {

        if (bien.getImages() == null) {
            bien.setImages(new ArrayList<>());
        }

        // Associer chaque image au bien
        bien.getImages().forEach(img -> img.setBien(bien));

        return bienRepository.save(bien);
    }

    // ==========================
    // UPDATE
    // ==========================

    private BienImmobilier updateBien(BienImmobilier bien) {

        BienImmobilier existingBien = bienRepository.findById(bien.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Bien non trouvé avec l'ID: " + bien.getId()
                ));

        // Champs communs
        existingBien.setTitre(bien.getTitre());
        existingBien.setDescription(bien.getDescription());
        existingBien.setPrix(bien.getPrix());
        existingBien.setSurface(bien.getSurface());
        existingBien.setAdresse(bien.getAdresse());
        existingBien.setVille(bien.getVille());
        existingBien.setCodePostal(bien.getCodePostal());
        existingBien.setQuartier(bien.getQuartier());
        existingBien.setLatitude(bien.getLatitude());
        existingBien.setLongitude(bien.getLongitude());
        existingBien.setTypeTransaction(bien.getTypeTransaction());
        existingBien.setStatut(bien.getStatut());
        existingBien.setMisEnAvant(bien.getMisEnAvant());
        existingBien.setOrdreAffichage(bien.getOrdreAffichage());

        // Champs spécifiques (héritage)
        updateSpecificFields(existingBien, bien);

        // Gestion des images

        // 1️⃣ Supprimer les images existantes
        imageBienRepository.findAll().stream()
                .filter(img -> img.getBien().getId().equals(existingBien.getId()))
                .forEach(imageBienRepository::delete);

        // 2️⃣ Nettoyer la collection côté Java
        existingBien.getImages().clear();

        // 3️⃣ Ajouter les nouvelles images
        if (bien.getImages() != null) {
            bien.getImages().forEach(img -> {
                img.setBien(existingBien);
                existingBien.getImages().add(img);
            });
        }

        return bienRepository.save(existingBien);
    }

    // ==========================
    // HÉRITAGE
    // ==========================

    private void updateSpecificFields(BienImmobilier existingBien, BienImmobilier newBien) {

        // On ne lance plus d'exception ici car on gère le changement de type dans saveBien

        if (existingBien instanceof Appartement e && newBien instanceof Appartement n) {
            e.setNombrePieces(n.getNombrePieces());
            e.setNombreChambres(n.getNombreChambres());
            e.setNombreSallesBain(n.getNombreSallesBain());
            e.setEtage(n.getEtage());
            e.setAscenseur(n.getAscenseur());
            e.setBalcon(n.getBalcon());
            e.setParking(n.getParking());
            e.setMeuble(n.getMeuble());
            e.setClimatisation(n.getClimatisation());
        }

        else if (existingBien instanceof Villa e && newBien instanceof Villa n) {
            e.setNombrePieces(n.getNombrePieces());
            e.setNombreChambres(n.getNombreChambres());
            e.setNombreSallesBain(n.getNombreSallesBain());
            e.setJardin(n.getJardin());
            e.setGarage(n.getGarage());
            e.setPiscine(n.getPiscine());
            e.setClimatisation(n.getClimatisation());
            e.setNombreEtages(n.getNombreEtages());
            e.setGardien(n.getGardien());
        }

        else if (existingBien instanceof Maison e && newBien instanceof Maison n) {
            e.setNombrePieces(n.getNombrePieces());
            e.setNombreChambres(n.getNombreChambres());
            e.setNombreSallesBain(n.getNombreSallesBain());
            e.setJardin(n.getJardin());
            e.setGarage(n.getGarage());
            e.setPiscine(n.getPiscine());
            e.setClimatisation(n.getClimatisation());
        }

        else if (existingBien instanceof Bureau e && newBien instanceof Bureau n) {
            e.setSuperficieBureau(n.getSuperficieBureau());
            e.setNombreBureaux(n.getNombreBureaux());
            e.setParking(n.getParking());
            e.setClimatisation(n.getClimatisation());
            e.setSecurite(n.getSecurite());
            e.setEtage(n.getEtage());
            e.setAscenseur(n.getAscenseur());
        }

        else if (existingBien instanceof Terrain e && newBien instanceof Terrain n) {
            e.setTypeConstruction(n.getTypeConstruction());
            e.setViabilise(n.getViabilise());
            e.setZonage(n.getZonage());
        }
    }

    // ==========================
    // DELETE
    // ==========================

    public void deleteBien(Long id) {
        bienRepository.deleteById(id);
    }
}
