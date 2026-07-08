package com.glab.model;

/**
 * Automate industriel (Modbus, Profinet, etc.).
 */
public class EquipementAutomate extends Ressource {

    private String marque;
    private int nombreEntreesSorties;
    private String protocoleReseau;

    public EquipementAutomate(int id, String designation, String emplacement, int quantite,
                              String marque, int nombreEntreesSorties, String protocoleReseau) {
        super(id, designation, emplacement, quantite);
        this.marque = marque;
        this.nombreEntreesSorties = nombreEntreesSorties;
        this.protocoleReseau = protocoleReseau;
    }

    public EquipementAutomate(String designation, String emplacement, int quantite,
                              String marque, int nombreEntreesSorties, String protocoleReseau) {
        super(designation, emplacement, quantite);
        this.marque = marque;
        this.nombreEntreesSorties = nombreEntreesSorties;
        this.protocoleReseau = protocoleReseau;
    }

    @Override
    public String getType() {
        return "AUTOMATE";
    }

    @Override
    public String getDiagnostic() {
        return String.format("Marque: %s | E/S: %d | Protocole: %s",
                marque, nombreEntreesSorties, protocoleReseau);
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getNombreEntreesSorties() {
        return nombreEntreesSorties;
    }

    public void setNombreEntreesSorties(int nombreEntreesSorties) {
        this.nombreEntreesSorties = nombreEntreesSorties;
    }

    public String getProtocoleReseau() {
        return protocoleReseau;
    }

    public void setProtocoleReseau(String protocoleReseau) {
        this.protocoleReseau = protocoleReseau;
    }
}
