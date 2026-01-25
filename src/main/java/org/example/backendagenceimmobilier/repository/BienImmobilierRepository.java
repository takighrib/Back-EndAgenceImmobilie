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

    // ✅ Correction : Utiliser une requête JPQL avec JOIN FETCH
    @Query("SELECT DISTINCT b FROM BienImmobilier b LEFT JOIN FETCH b.images WHERE b.statut = :statut")
    List<BienImmobilier> findByStatut(@Param("statut") StatutBien statut);

    // ✅ Correction : Ajouter JOIN FETCH
    @Query("SELECT DISTINCT b FROM BienImmobilier b LEFT JOIN FETCH b.images WHERE b.misEnAvant = true ORDER BY b.ordreAffichage ASC")
    List<BienImmobilier> findByMisEnAvantTrueOrderByOrdreAffichageAsc();

    // ✅ Correction : Ajouter JOIN FETCH
    @Query("SELECT DISTINCT b FROM BienImmobilier b LEFT JOIN FETCH b.images WHERE LOWER(b.ville) LIKE LOWER(CONCAT('%', :ville, '%'))")
    List<BienImmobilier> findByVilleContainingIgnoreCase(@Param("ville") String ville);

    // ✅ Correction : Ajouter JOIN FETCH
    @Query("SELECT DISTINCT b FROM BienImmobilier b LEFT JOIN FETCH b.images WHERE b.typeTransaction = :typeTransaction")
    List<BienImmobilier> findByTypeTransaction(@Param("typeTransaction") TypeTransaction typeTransaction);

    // Recherche avancée - déjà correcte
    @Query("SELECT DISTINCT b FROM BienImmobilier b LEFT JOIN FETCH b.images WHERE " +
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

    // Trouver les biens récents - déjà correcte
    @Query("SELECT DISTINCT b FROM BienImmobilier b LEFT JOIN FETCH b.images WHERE b.statut = 'DISPONIBLE' ORDER BY b.datePublication DESC")
    List<BienImmobilier> findBiensRecents();
}