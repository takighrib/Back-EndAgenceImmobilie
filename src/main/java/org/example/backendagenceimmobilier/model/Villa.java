package org.example.backendagenceimmobilier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "villa")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Villa extends BienImmobilier {

    private Integer nombrePieces;

    private Integer nombreChambres;

    private Integer nombreSallesBain;

    private Boolean jardin = false;

    private Boolean garage = false;

    private Boolean piscine = false;

    private Boolean climatisation = false;

    private Integer nombreEtages;

    private Boolean gardien = false;
}