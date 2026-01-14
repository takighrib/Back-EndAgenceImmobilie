package org.example.backendagenceimmobilier.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image_bien")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageBien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String urlImage;

    @Column(nullable = false)
    private Integer ordre = 0;

    @Column(nullable = false)
    private Boolean estPrincipale = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_id", nullable = false)
    @JsonIgnore
    private BienImmobilier bien;

    public ImageBien(String urlImage, Integer ordre, Boolean estPrincipale) {
        this.urlImage = urlImage;
        this.ordre = ordre;
        this.estPrincipale = estPrincipale;
    }
}
