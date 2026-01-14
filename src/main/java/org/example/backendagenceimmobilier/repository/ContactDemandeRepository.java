package org.example.backendagenceimmobilier.repository;

import org.example.backendagenceimmobilier.model.ContactDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDemandeRepository extends JpaRepository<ContactDemande, Long> {

    List<ContactDemande> findByStatutOrderByDateDemandeDesc(String statut);

    List<ContactDemande> findAllByOrderByDateDemandeDesc();
}
