package org.example.backendagenceimmobilier.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bureau")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Bureau extends BienImmobilier {

    private Double superficieBureau;

    private Integer nombreBureaux;

    private Boolean parking = false;

    private Boolean climatisation = false;

    private Boolean securite = false;

    private Integer etage;

    private Boolean ascenseur = false;
}
