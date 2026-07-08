package com.glab.controller;

import com.glab.database.RessourceDAO;
import com.glab.model.DocumentationTechnique;
import com.glab.model.EquipementAutomate;
import com.glab.model.EquipementDrone;
import com.glab.model.Ressource;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Contrôleur métier : sépare la logique de la vue Swing et de la persistance JDBC.
 */
public class RessourceController {

    private final RessourceDAO dao;
    private List<Ressource> ressources;

    public RessourceController() {
        this.dao = new RessourceDAO();
        this.ressources = new ArrayList<>();
    }

    public void chargerRessources() {
        ressources = dao.chargerToutesLesRessources();
    }

    public List<Ressource> getRessources() {
        return new ArrayList<>(ressources);
    }

    public List<Ressource> filtrer(String critere) {
        if (critere == null || critere.isBlank()) {
            return getRessources();
        }

        String recherche = critere.toLowerCase(Locale.ROOT);
        List<Ressource> resultat = new ArrayList<>();

        for (Ressource ressource : ressources) {
            if (ressource.getDesignation().toLowerCase(Locale.ROOT).contains(recherche)
                    || ressource.getType().toLowerCase(Locale.ROOT).contains(recherche)
                    || ressource.getEmplacement().toLowerCase(Locale.ROOT).contains(recherche)) {
                resultat.add(ressource);
            }
        }

        return resultat;
    }

    public void ajouterAutomate(String designation, String emplacement, int quantite,
                                String marque, int nombreEntreesSorties, String protocoleReseau) {
        EquipementAutomate automate = new EquipementAutomate(
                designation, emplacement, quantite, marque, nombreEntreesSorties, protocoleReseau
        );
        dao.ajouter(automate);
        ressources.add(automate);
    }

    public void ajouterDrone(String designation, String emplacement, int quantite,
                             double masse, int autonomieVol, String typeCapteur) {
        EquipementDrone drone = new EquipementDrone(
                designation, emplacement, quantite, masse, autonomieVol, typeCapteur
        );
        dao.ajouter(drone);
        ressources.add(drone);
    }

    public void ajouterDocumentation(String designation, String emplacement, int quantite,
                                     String auteur, int nombrePages, String lienPdf) {
        DocumentationTechnique doc = new DocumentationTechnique(
                designation, emplacement, quantite, auteur, nombrePages, lienPdf
        );
        dao.ajouter(doc);
        ressources.add(doc);
    }

    public void supprimer(Ressource ressource) {
        dao.supprimer(ressource.getId());
        ressources.removeIf(r -> r.getId() == ressource.getId());
    }

    public void validerChampsCommuns(String designation, String emplacement, String quantiteTexte)
            throws ValidationException {
        if (designation == null || designation.isBlank()) {
            throw new ValidationException("La désignation est obligatoire.");
        }
        if (emplacement == null || emplacement.isBlank()) {
            throw new ValidationException("L'emplacement est obligatoire.");
        }
        if (quantiteTexte == null || quantiteTexte.isBlank()) {
            throw new ValidationException("La quantité est obligatoire.");
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteTexte.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("La quantité doit être un nombre entier valide.");
        }

        if (quantite < 0) {
            throw new ValidationException("La quantité ne peut pas être négative.");
        }
    }

    public int parserEntierPositif(String valeur, String nomChamp) throws ValidationException {
        if (valeur == null || valeur.isBlank()) {
            throw new ValidationException(nomChamp + " est obligatoire.");
        }
        try {
            int nombre = Integer.parseInt(valeur.trim());
            if (nombre < 0) {
                throw new ValidationException(nomChamp + " ne peut pas être négatif.");
            }
            return nombre;
        } catch (NumberFormatException e) {
            throw new ValidationException(nomChamp + " doit être un nombre entier valide.");
        }
    }

    public double parserDecimalPositif(String valeur, String nomChamp) throws ValidationException {
        if (valeur == null || valeur.isBlank()) {
            throw new ValidationException(nomChamp + " est obligatoire.");
        }
        try {
            double nombre = Double.parseDouble(valeur.trim().replace(',', '.'));
            if (nombre < 0) {
                throw new ValidationException(nomChamp + " ne peut pas être négatif.");
            }
            return nombre;
        } catch (NumberFormatException e) {
            throw new ValidationException(nomChamp + " doit être un nombre décimal valide.");
        }
    }

    public void validerTexte(String valeur, String nomChamp) throws ValidationException {
        if (valeur == null || valeur.isBlank()) {
            throw new ValidationException(nomChamp + " est obligatoire.");
        }
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
