package org.example.backendagenceimmobilier.config;


import org.example.backendagenceimmobilier.model.*;
import org.example.backendagenceimmobilier.repository.BienImmobilierRepository;
import org.example.backendagenceimmobilier.repository.ConfigurationAgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BienImmobilierRepository bienRepository;
    private final ConfigurationAgenceRepository agenceRepository;

    @Override
    public void run(String... args) {
        // Configuration de l'agence
        ConfigurationAgence agence = new ConfigurationAgence();
        agence.setNomAgence("Agence Immobilière Premium");
        agence.setEmail("contact@agence-premium.tn");
        agence.setTelephone("+216 70 123 456");
        agence.setAdresse("Avenue Habib Bourguiba, Tunis");
        agence.setDescription("Votre partenaire de confiance pour tous vos projets immobiliers en Tunisie");
        agence.setWhatsappNumber("+21670123456");
        agenceRepository.save(agence);

        // Villa de luxe
        Villa villa = new Villa();
        villa.setTitre("Villa de Luxe avec Piscine");
        villa.setDescription("Magnifique villa moderne située dans un quartier résidentiel calme. Cette propriété exceptionnelle offre un cadre de vie idéal avec ses espaces généreux et ses finitions haut de gamme.");
        villa.setAdresse("Rue du Lac, Lac 2");
        villa.setVille("Tunis");
        villa.setCodePostal("1053");
        villa.setQuartier("Les Berges du Lac");
        villa.setLatitude(36.8418);
        villa.setLongitude(10.2398);
        villa.setSurface(450.0);
        villa.setPrix(850000.0);
        villa.setStatut(StatutBien.DISPONIBLE);
        villa.setTypeTransaction(TypeTransaction.VENTE);
        villa.setNombrePieces(8);
        villa.setNombreChambres(5);
        villa.setNombreSallesBain(3);
        villa.setJardin(true);
        villa.setGarage(true);
        villa.setPiscine(true);
        villa.setClimatisation(true);
        villa.setNombreEtages(2);
        villa.setGardien(true);
        villa.setMisEnAvant(true);
        villa.setOrdreAffichage(1);

        ImageBien villaImg1 = new ImageBien("C:/Users/takig/OneDrive/Bureau/icon", 1, true);
        ImageBien villaImg2 = new ImageBien("https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=800", 2, false);
        ImageBien villaImg3 = new ImageBien("https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?w=800", 3, false);
        villaImg1.setBien(villa);
        villaImg2.setBien(villa);
        villaImg3.setBien(villa);
        villa.setImages(new ArrayList<>());
        villa.getImages().add(villaImg1);
        villa.getImages().add(villaImg2);
        villa.getImages().add(villaImg3);

        // Appartement moderne
        Appartement appt = new Appartement();
        appt.setTitre("Appartement S+3 Vue Mer");
        appt.setDescription("Superbe appartement lumineux avec une vue imprenable sur la mer. Proche de toutes commodités, dans une résidence sécurisée avec piscine commune.");
        appt.setAdresse("Avenue Mohamed V");
        appt.setVille("La Marsa");
        appt.setCodePostal("2078");
        appt.setQuartier("Marsa Plage");
        appt.setLatitude(36.8785);
        appt.setLongitude(10.3247);
        appt.setSurface(150.0);
        appt.setPrix(320000.0);
        appt.setStatut(StatutBien.DISPONIBLE);
        appt.setTypeTransaction(TypeTransaction.VENTE);
        appt.setNombrePieces(4);
        appt.setNombreChambres(3);
        appt.setNombreSallesBain(2);
        appt.setEtage(5);
        appt.setAscenseur(true);
        appt.setBalcon(true);
        appt.setParking(true);
        appt.setClimatisation(true);
        appt.setMeuble(false);
        appt.setMisEnAvant(true);
        appt.setOrdreAffichage(2);

        ImageBien apptImg1 = new ImageBien("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800", 1, true);
        ImageBien apptImg2 = new ImageBien("https://images.unsplash.com/photo-1502672260066-6bc356a06721?w=800", 2, false);
        apptImg1.setBien(appt);
        apptImg2.setBien(appt);
        appt.setImages(new ArrayList<>());
        appt.getImages().add(apptImg1);
        appt.getImages().add(apptImg2);

        // Maison familiale
        Maison maison = new Maison();
        maison.setTitre("Maison Familiale Spacieuse");
        maison.setDescription("Charmante maison idéale pour une famille, avec jardin et garage. Quartier calme et recherché, proche des écoles et commerces.");
        maison.setAdresse("Rue de la République");
        maison.setVille("Sousse");
        maison.setCodePostal("4000");
        maison.setQuartier("Sahloul");
        maison.setLatitude(35.8256);
        maison.setLongitude(10.6369);
        maison.setSurface(280.0);
        maison.setPrix(420000.0);
        maison.setStatut(StatutBien.DISPONIBLE);
        maison.setTypeTransaction(TypeTransaction.VENTE);
        maison.setNombrePieces(6);
        maison.setNombreChambres(4);
        maison.setNombreSallesBain(2);
        maison.setJardin(true);
        maison.setGarage(true);
        maison.setPiscine(false);
        maison.setClimatisation(true);
        maison.setMisEnAvant(true);
        maison.setOrdreAffichage(3);

        ImageBien maisonImg = new ImageBien("https://images.unsplash.com/photo-1568605114967-8130f3a36994?w=800", 1, true);
        maisonImg.setBien(maison);
        maison.setImages(new ArrayList<>());
        maison.getImages().add(maisonImg);

        // Terrain constructible
        Terrain terrain = new Terrain();
        terrain.setTitre("Terrain Constructible 500m²");
        terrain.setDescription("Excellent terrain constructible dans une zone résidentielle en plein développement. Idéal pour construction de villa.");
        terrain.setAdresse("Zone Touristique");
        terrain.setVille("Hammamet");
        terrain.setCodePostal("8050");
        terrain.setQuartier("Yasmine");
        terrain.setLatitude(36.4000);
        terrain.setLongitude(10.6167);
        terrain.setSurface(500.0);
        terrain.setPrix(180000.0);
        terrain.setStatut(StatutBien.DISPONIBLE);
        terrain.setTypeTransaction(TypeTransaction.VENTE);
        terrain.setTypeConstruction("Résidentiel");
        terrain.setViabilise(true);
        terrain.setZonage("Zone résidentielle");

        ImageBien terrainImg = new ImageBien("https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=800", 1, true);
        terrainImg.setBien(terrain);
        terrain.setImages(new ArrayList<>());
        terrain.getImages().add(terrainImg);

        // Bureau professionnel
        Bureau bureau = new Bureau();
        bureau.setTitre("Bureau Moderne Centre Ville");
        bureau.setDescription("Espace de bureau moderne et lumineux, idéalement situé en plein centre-ville. Parfait pour activité professionnelle.");
        bureau.setAdresse("Avenue Bourguiba");
        bureau.setVille("Tunis");
        bureau.setCodePostal("1000");
        bureau.setQuartier("Centre Ville");
        bureau.setLatitude(36.8065);
        bureau.setLongitude(10.1815);
        bureau.setSurface(120.0);
        bureau.setPrix(2500.0);
        bureau.setStatut(StatutBien.DISPONIBLE);
        bureau.setTypeTransaction(TypeTransaction.LOCATION);
        bureau.setSuperficieBureau(120.0);
        bureau.setNombreBureaux(4);
        bureau.setParking(true);
        bureau.setClimatisation(true);
        bureau.setSecurite(true);
        bureau.setEtage(3);
        bureau.setAscenseur(true);

        ImageBien bureauImg = new ImageBien("https://images.unsplash.com/photo-1497366216548-37526070297c?w=800", 1, true);
        bureauImg.setBien(bureau);
        bureau.setImages(new ArrayList<>());
        bureau.getImages().add(bureauImg);

        // Appartement à louer
        Appartement apptLocation = new Appartement();
        apptLocation.setTitre("Appartement Meublé S+2");
        apptLocation.setDescription("Bel appartement entièrement meublé et équipé, prêt à être habité. Résidence standing avec toutes les commodités.");
        apptLocation.setAdresse("Rue Ibn Khaldoun");
        apptLocation.setVille("Tunis");
        apptLocation.setCodePostal("1002");
        apptLocation.setQuartier("Menzah 6");
        apptLocation.setLatitude(36.8344);
        apptLocation.setLongitude(10.1693);
        apptLocation.setSurface(100.0);
        apptLocation.setPrix(1200.0);
        apptLocation.setStatut(StatutBien.DISPONIBLE);
        apptLocation.setTypeTransaction(TypeTransaction.LOCATION);
        apptLocation.setNombrePieces(3);
        apptLocation.setNombreChambres(2);
        apptLocation.setNombreSallesBain(1);
        apptLocation.setEtage(2);
        apptLocation.setAscenseur(true);
        apptLocation.setBalcon(true);
        apptLocation.setParking(true);
        apptLocation.setClimatisation(true);
        apptLocation.setMeuble(true);

        ImageBien apptLocImg = new ImageBien("https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=800", 1, true);
        apptLocImg.setBien(apptLocation);
        apptLocation.setImages(new ArrayList<>());
        apptLocation.getImages().add(apptLocImg);

        // Sauvegarder tous les biens
        bienRepository.save(villa);
        bienRepository.save(appt);
        bienRepository.save(maison);
        bienRepository.save(terrain);
        bienRepository.save(bureau);
        bienRepository.save(apptLocation);

        System.out.println("✅ Données de test insérées avec succès !");
    }
}
