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
public class Appartement extends BienImmobilier {
    @Override
    @Transient
    public String getTypeBien() {
        return "Appartement";
    }
}
