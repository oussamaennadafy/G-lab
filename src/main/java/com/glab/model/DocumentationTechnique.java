package com.glab.model;

/**
 * Documentation technique (manuels, guides constructeur).
 */
public class DocumentationTechnique extends Ressource {

    private String auteurConstructeur;
    private int nombrePages;
    private String lienManuelPdf;

    public DocumentationTechnique(int id, String designation, String emplacement, int quantite,
                                  String auteurConstructeur, int nombrePages, String lienManuelPdf) {
        super(id, designation, emplacement, quantite);
        this.auteurConstructeur = auteurConstructeur;
        this.nombrePages = nombrePages;
        this.lienManuelPdf = lienManuelPdf;
    }

    public DocumentationTechnique(String designation, String emplacement, int quantite,
                                  String auteurConstructeur, int nombrePages, String lienManuelPdf) {
        super(designation, emplacement, quantite);
        this.auteurConstructeur = auteurConstructeur;
        this.nombrePages = nombrePages;
        this.lienManuelPdf = lienManuelPdf;
    }

    @Override
    public String getType() {
        return "DOCUMENTATION";
    }

    @Override
    public String getDiagnostic() {
        return String.format("Auteur: %s | Pages: %d | Manuel: %s",
                auteurConstructeur, nombrePages, lienManuelPdf);
    }

    public String getAuteurConstructeur() {
        return auteurConstructeur;
    }

    public void setAuteurConstructeur(String auteurConstructeur) {
        this.auteurConstructeur = auteurConstructeur;
    }

    public int getNombrePages() {
        return nombrePages;
    }

    public void setNombrePages(int nombrePages) {
        this.nombrePages = nombrePages;
    }

    public String getLienManuelPdf() {
        return lienManuelPdf;
    }

    public void setLienManuelPdf(String lienManuelPdf) {
        this.lienManuelPdf = lienManuelPdf;
    }
}
