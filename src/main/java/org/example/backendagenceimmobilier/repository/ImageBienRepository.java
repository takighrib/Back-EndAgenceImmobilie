package org.example.backendagenceimmobilier.repository;

import org.example.backendagenceimmobilier.model.ImageBien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageBienRepository extends JpaRepository<ImageBien, Long> {
}
