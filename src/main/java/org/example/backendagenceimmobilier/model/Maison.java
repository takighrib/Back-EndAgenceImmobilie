package org.example.backendagenceimmobilier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "maison")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Maison extends BienImmobilier {

    private Integer nombrePieces;

    private Integer nombreChambres;

    private Integer nombreSallesBain;

    private Boolean jardin = false;

    private Boolean garage = false;

    private Boolean piscine = false;

    private Boolean climatisation = false;

    @Override
    @Transient
    public String getTypeBien() {
        return "Appartement";
    }
}