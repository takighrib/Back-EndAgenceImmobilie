package org.example.backendagenceimmobilier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appartement")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Appartement extends BienImmobilier {

    private Integer nombrePieces;

    private Integer nombreChambres;

    private Integer nombreSallesBain;

    private Integer etage;

    private Boolean ascenseur = false;

    private Boolean balcon = false;

    private Boolean parking = false;

    private Boolean climatisation = false;

    private Boolean meuble = false;
    @Override
    @Transient
    public String getTypeBien() {
        return "Appartement";
    }
}
