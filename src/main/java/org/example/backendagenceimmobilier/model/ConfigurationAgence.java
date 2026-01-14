package org.example.backendagenceimmobilier.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "configuration_agence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationAgence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomAgence;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String adresse;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logoUrl;

    private String facebookUrl;

    private String instagramUrl;

    private String linkedinUrl;

    private String whatsappNumber;
}
