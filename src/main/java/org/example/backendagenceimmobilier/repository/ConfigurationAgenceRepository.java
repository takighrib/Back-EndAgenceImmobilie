package org.example.backendagenceimmobilier.repository;

import org.example.backendagenceimmobilier.model.ConfigurationAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationAgenceRepository extends JpaRepository<ConfigurationAgence, Long> {
}
