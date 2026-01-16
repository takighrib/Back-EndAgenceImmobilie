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
        include = JsonTypeInfo.As.PROPERTY,
        property = "typeBien"  // nom du champ dans le JSON qui dira quel type concret instancier
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

    @OneToMany(mappedBy = "bien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageBien> images = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        datePublication = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }

    // Méthode utilitaire pour obtenir le type de bien
    public String getTypeBien() {
        return this.getClass().getSimpleName();
    }
}

