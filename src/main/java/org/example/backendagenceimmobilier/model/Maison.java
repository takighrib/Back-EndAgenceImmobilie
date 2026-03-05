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
public class Maison extends BienImmobilier {
    @Override
    @Transient
    public String getTypeBien() {
        return "Maison";
    }
}
