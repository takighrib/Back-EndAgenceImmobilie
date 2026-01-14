package org.example.backendagenceimmobilier.repository;


import org.example.backendagenceimmobilier.model.BienImmobilier;
import org.example.backendagenceimmobilier.model.StatutBien;
import org.example.backendagenceimmobilier.model.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienImmobilierRepository extends JpaRepository<BienImmobilier, Long> {

    // Trouver tous les biens disponibles
    List<BienImmobilier> findByStatut(StatutBien statut);

    // Trouver les biens mis en avant
    List<BienImmobilier> findByMisEnAvantTrueOrderByOrdreAffichageAsc();

    // Trouver par ville
    List<BienImmobilier> findByVilleContainingIgnoreCase(String ville);

    // Trouver par type de transaction
    List<BienImmobilier> findByTypeTransaction(TypeTransaction typeTransaction);

    // Recherche avancée
    @Query("SELECT b FROM BienImmobilier b WHERE " +
            "(:ville IS NULL OR LOWER(b.ville) LIKE LOWER(CONCAT('%', :ville, '%'))) AND " +
            "(:prixMin IS NULL OR b.prix >= :prixMin) AND " +
            "(:prixMax IS NULL OR b.prix <= :prixMax) AND " +
            "(:surfaceMin IS NULL OR b.surface >= :surfaceMin) AND " +
            "(:typeTransaction IS NULL OR b.typeTransaction = :typeTransaction) AND " +
            "b.statut = 'DISPONIBLE' " +
            "ORDER BY b.datePublication DESC")
    List<BienImmobilier> rechercheAvancee(
            @Param("ville") String ville,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax,
            @Param("surfaceMin") Double surfaceMin,
            @Param("typeTransaction") TypeTransaction typeTransaction
    );

    // Trouver les biens récents
    @Query("SELECT b FROM BienImmobilier b WHERE b.statut = 'DISPONIBLE' ORDER BY b.datePublication DESC")
    List<BienImmobilier> findBiensRecents();
}
