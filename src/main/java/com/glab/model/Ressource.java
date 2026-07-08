package com.glab.model;

/**
 * Classe abstraite représentant une ressource du laboratoire.
 */
public abstract class Ressource {

    private int id;
    private String designation;
    private String emplacement;
    private int quantite;

    public Ressource(int id, String designation, String emplacement, int quantite) {
        this.id = id;
        this.designation = designation;
        this.emplacement = emplacement;
        this.quantite = quantite;
    }

    public Ressource(String designation, String emplacement, int quantite) {
        this(0, designation, emplacement, quantite);
    }

    public abstract String getType();

    public abstract String getDiagnostic();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return getType() + " - " + designation + " (" + emplacement + ")";
    }
}
