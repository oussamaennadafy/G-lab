package com.glab.model;

/**
 * Drone équipé de capteurs embarqués.
 */
public class EquipementDrone extends Ressource {

    private double masse;
    private int autonomieVol;
    private String typeCapteurEmbarque;

    public EquipementDrone(int id, String designation, String emplacement, int quantite,
                           double masse, int autonomieVol, String typeCapteurEmbarque) {
        super(id, designation, emplacement, quantite);
        this.masse = masse;
        this.autonomieVol = autonomieVol;
        this.typeCapteurEmbarque = typeCapteurEmbarque;
    }

    public EquipementDrone(String designation, String emplacement, int quantite,
                           double masse, int autonomieVol, String typeCapteurEmbarque) {
        super(designation, emplacement, quantite);
        this.masse = masse;
        this.autonomieVol = autonomieVol;
        this.typeCapteurEmbarque = typeCapteurEmbarque;
    }

    @Override
    public String getType() {
        return "DRONE";
    }

    @Override
    public String getDiagnostic() {
        return String.format("Masse: %.2f kg | Autonomie: %d min | Capteur: %s",
                masse, autonomieVol, typeCapteurEmbarque);
    }

    public double getMasse() {
        return masse;
    }

    public void setMasse(double masse) {
        this.masse = masse;
    }

    public int getAutonomieVol() {
        return autonomieVol;
    }

    public void setAutonomieVol(int autonomieVol) {
        this.autonomieVol = autonomieVol;
    }

    public String getTypeCapteurEmbarque() {
        return typeCapteurEmbarque;
    }

    public void setTypeCapteurEmbarque(String typeCapteurEmbarque) {
        this.typeCapteurEmbarque = typeCapteurEmbarque;
    }
}
