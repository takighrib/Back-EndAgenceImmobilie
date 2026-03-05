package org.example.backendagenceimmobilier.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "typeBien",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Appartement.class, name = "Appartement"),
        @JsonSubTypes.Type(value = Maison.class, name = "Maison"),
        @JsonSubTypes.Type(value = Bureau.class, name = "Bureau"),
        @JsonSubTypes.Type(value = Terrain.class, name = "Terrain"),
        @JsonSubTypes.Type(value = Villa.class, name = "Villa")
})
@Entity
@Table(name = "bien_immobilier")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BienImmobilier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference", unique = true, updatable = false)
    private String reference;
    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String ville;

    private String codePostal;
    private String quartier;
    private Double latitude;
    private Double longitude;

    @Column(nullable = false)
    private Double surface;

    @Column(nullable = false)
    private Double prix;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutBien statut = StatutBien.DISPONIBLE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransaction typeTransaction;

    @Column(nullable = false, updatable = false)
    private LocalDateTime datePublication;

    private LocalDateTime dateModification;

    @Column(nullable = false)
    private Boolean misEnAvant = false;

    private Integer ordreAffichage = 0;


private Integer nombrePieces;
private Integer nombreChambres;
private Integer nombreSallesBain;

// Équipements extérieurs
private Boolean jardin = false;
private Boolean garage = false;
private Boolean piscine = false;

// Confort
private Boolean climatisation = false;
private Boolean parking = false;
private Boolean balcon = false;
private Boolean meuble = false;
private Boolean ascenseur = false;
private Boolean gardien = false;

// Étages
private Integer etage;
private Integer nombreEtages;

// Bureau
private Double superficieBureau;
private Integer nombreBureaux;
private Boolean securite = false;

// Terrain
private String typeConstruction;
private Boolean viabilise = false;
private String zonage;

    // ✅ CORRECTION : Relation unidirectionnelle simplifiée
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageBien> images = new ArrayList<>();

    // ✅ NOUVELLE APPROCHE : Champ persisté en base
    @Column(name = "type_bien", insertable = false, updatable = false)
    private String typeBien;

    @PrePersist
    protected void onCreate() {
        datePublication = LocalDateTime.now();
        dateModification = LocalDateTime.now();
        // Générer la référence une seule fois
        if (this.reference == null) {
            this.reference = "HI-" + System.currentTimeMillis();
        }
    }
@PostLoad
@PostPersist
@PostUpdate
protected void updateTypeBien() {
    this.typeBien = this.getClass().getSimpleName();
}
    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }


}
