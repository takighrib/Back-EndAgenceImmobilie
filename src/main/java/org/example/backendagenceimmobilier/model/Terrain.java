package org.example.backendagenceimmobilier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "terrain")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Terrain extends BienImmobilier {

    private String typeConstruction; // "Constructible", "Agricole", etc.

    private Boolean viabilise = false; // Eau, électricité, etc.

    private String zonage; // "Résidentiel", "Commercial", "Industriel"

    @Override
    @Transient
    public String getTypeBien() {
        return "Appartement";
    }
}