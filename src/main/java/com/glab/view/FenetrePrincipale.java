package com.glab.view;

import com.glab.controller.RessourceController;
import com.glab.model.Ressource;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

/**
 * Vue principale Swing (MVC) : affichage, formulaire adaptatif et filtrage.
 */
public class FenetrePrincipale extends JFrame {

    private static final String[] TYPES_RESSOURCE = {
            "AUTOMATE", "DRONE", "DOCUMENTATION"
    };

    private static final String[] COLONNES_TABLEAU = {
            "ID", "Type", "Désignation", "Emplacement", "Quantité", "Diagnostic"
    };

    private final RessourceController controller;

    private DefaultTableModel modeleTableau;
    private JTable tableRessources;
    private TableRowSorter<DefaultTableModel> trieurTableau;
    private JTextField champRecherche;

    private JComboBox<String> comboTypeRessource;
    private JTextField champDesignation;
    private JTextField champEmplacement;
    private JTextField champQuantite;

    private JLabel labelMarque;
    private JTextField champMarque;
    private JLabel labelNombreES;
    private JTextField champNombreES;
    private JLabel labelProtocole;
    private JTextField champProtocole;

    private JLabel labelMasse;
    private JTextField champMasse;
    private JLabel labelAutonomie;
    private JTextField champAutonomie;
    private JLabel labelCapteur;
    private JTextField champCapteur;

    private JLabel labelAuteur;
    private JTextField champAuteur;
    private JLabel labelNombrePages;
    private JTextField champNombrePages;
    private JLabel labelLienPdf;
    private JTextField champLienPdf;

    public FenetrePrincipale(RessourceController controller) {
        this.controller = controller;
        initialiserInterface();
        controller.chargerRessources();
        rafraichirTableau(controller.getRessources());
    }

    private void initialiserInterface() {
        setTitle("G-Lab - Gestion du Parc de Laboratoire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel conteneurPrincipal = new JPanel(new BorderLayout(10, 10));
        conteneurPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        conteneurPrincipal.add(creerPanneauRecherche(), BorderLayout.NORTH);
        conteneurPrincipal.add(creerPanneauTableau(), BorderLayout.CENTER);
        conteneurPrincipal.add(creerPanneauFormulaire(), BorderLayout.SOUTH);

        add(conteneurPrincipal);
    }

    private JPanel creerPanneauRecherche() {
        JPanel panneau = new JPanel(new BorderLayout(8, 0));
        panneau.setBorder(new TitledBorder("Recherche / Filtrage"));

        champRecherche = new JTextField();
        champRecherche.setToolTipText("Filtrer par désignation, type ou emplacement");

        champRecherche.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                appliquerFiltre();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                appliquerFiltre();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                appliquerFiltre();
            }
        });

        panneau.add(new JLabel("Rechercher : "), BorderLayout.WEST);
        panneau.add(champRecherche, BorderLayout.CENTER);
        return panneau;
    }

    private JPanel creerPanneauTableau() {
        JPanel panneau = new JPanel(new BorderLayout());
        panneau.setBorder(new TitledBorder("Stock du laboratoire"));

        modeleTableau = new DefaultTableModel(COLONNES_TABLEAU, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRessources = new JTable(modeleTableau);
        tableRessources.setAutoCreateRowSorter(true);
        tableRessources.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        trieurTableau = new TableRowSorter<>(modeleTableau);
        tableRessources.setRowSorter(trieurTableau);

        panneau.add(new JScrollPane(tableRessources), BorderLayout.CENTER);
        return panneau;
    }

    private JPanel creerPanneauFormulaire() {
        JPanel panneau = new JPanel(new BorderLayout(8, 8));
        panneau.setBorder(new TitledBorder("Ajouter une ressource"));

        JPanel grilleFormulaire = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        int ligne = 0;

        comboTypeRessource = new JComboBox<>(TYPES_RESSOURCE);
        comboTypeRessource.addActionListener(e -> adapterChampsSpecifiques());

        champDesignation = new JTextField(20);
        champEmplacement = new JTextField(20);
        champQuantite = new JTextField(10);

        champMarque = new JTextField(20);
        champNombreES = new JTextField(10);
        champProtocole = new JTextField(20);

        champMasse = new JTextField(10);
        champAutonomie = new JTextField(10);
        champCapteur = new JTextField(20);

        champAuteur = new JTextField(20);
        champNombrePages = new JTextField(10);
        champLienPdf = new JTextField(20);

        labelMarque = new JLabel("Marque :");
        labelNombreES = new JLabel("Nb E/S :");
        labelProtocole = new JLabel("Protocole réseau :");

        labelMasse = new JLabel("Masse (kg) :");
        labelAutonomie = new JLabel("Autonomie (min) :");
        labelCapteur = new JLabel("Capteur embarqué :");

        labelAuteur = new JLabel("Auteur / Constructeur :");
        labelNombrePages = new JLabel("Nombre de pages :");
        labelLienPdf = new JLabel("Lien manuel PDF :");

        ajouterChamp(grilleFormulaire, gbc, ligne++, "Type :", comboTypeRessource);
        ajouterChamp(grilleFormulaire, gbc, ligne++, "Désignation :", champDesignation);
        ajouterChamp(grilleFormulaire, gbc, ligne++, "Emplacement :", champEmplacement);
        ajouterChamp(grilleFormulaire, gbc, ligne++, "Quantité :", champQuantite);

        ajouterChamp(grilleFormulaire, gbc, ligne++, labelMarque, champMarque);
        ajouterChamp(grilleFormulaire, gbc, ligne++, labelNombreES, champNombreES);
        ajouterChamp(grilleFormulaire, gbc, ligne++, labelProtocole, champProtocole);

        ajouterChamp(grilleFormulaire, gbc, ligne++, labelMasse, champMasse);
        ajouterChamp(grilleFormulaire, gbc, ligne++, labelAutonomie, champAutonomie);
        ajouterChamp(grilleFormulaire, gbc, ligne++, labelCapteur, champCapteur);

        ajouterChamp(grilleFormulaire, gbc, ligne++, labelAuteur, champAuteur);
        ajouterChamp(grilleFormulaire, gbc, ligne++, labelNombrePages, champNombrePages);
        ajouterChamp(grilleFormulaire, gbc, ligne, labelLienPdf, champLienPdf);

        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton boutonAjouter = new JButton("Ajouter");
        JButton boutonSupprimer = new JButton("Supprimer la sélection");
        JButton boutonRafraichir = new JButton("Rafraîchir");

        boutonAjouter.addActionListener(e -> traiterAjout());
        boutonSupprimer.addActionListener(e -> traiterSuppression());
        boutonRafraichir.addActionListener(e -> {
            controller.chargerRessources();
            rafraichirTableau(controller.getRessources());
            appliquerFiltre();
        });

        panneauBoutons.add(boutonRafraichir);
        panneauBoutons.add(boutonSupprimer);
        panneauBoutons.add(boutonAjouter);

        panneau.add(grilleFormulaire, BorderLayout.CENTER);
        panneau.add(panneauBoutons, BorderLayout.SOUTH);

        adapterChampsSpecifiques();
        return panneau;
    }

    private void ajouterChamp(JPanel panneau, GridBagConstraints gbc, int ligne, Object label, java.awt.Component champ) {
        gbc.gridx = 0;
        gbc.gridy = ligne;
        gbc.weightx = 0;

        if (label instanceof JLabel jLabel) {
            panneau.add(jLabel, gbc);
        } else if (label instanceof String text) {
            panneau.add(new JLabel(text), gbc);
        } else if (label instanceof java.awt.Component component) {
            panneau.add(component, gbc);
        }

        gbc.gridx = 1;
        gbc.weightx = 1;
        panneau.add(champ, gbc);
    }

    private void adapterChampsSpecifiques() {
        String type = (String) comboTypeRessource.getSelectedItem();

        boolean automate = "AUTOMATE".equals(type);
        boolean drone = "DRONE".equals(type);
        boolean documentation = "DOCUMENTATION".equals(type);

        labelMarque.setEnabled(automate);
        champMarque.setEnabled(automate);
        labelNombreES.setEnabled(automate);
        champNombreES.setEnabled(automate);
        labelProtocole.setEnabled(automate);
        champProtocole.setEnabled(automate);

        labelMasse.setEnabled(drone);
        champMasse.setEnabled(drone);
        labelAutonomie.setEnabled(drone);
        champAutonomie.setEnabled(drone);
        labelCapteur.setEnabled(drone);
        champCapteur.setEnabled(drone);

        labelAuteur.setEnabled(documentation);
        champAuteur.setEnabled(documentation);
        labelNombrePages.setEnabled(documentation);
        champNombrePages.setEnabled(documentation);
        labelLienPdf.setEnabled(documentation);
        champLienPdf.setEnabled(documentation);
    }

    private void traiterAjout() {
        try {
            String type = (String) comboTypeRessource.getSelectedItem();
            String designation = champDesignation.getText();
            String emplacement = champEmplacement.getText();
            String quantiteTexte = champQuantite.getText();

            controller.validerChampsCommuns(designation, emplacement, quantiteTexte);
            int quantite = Integer.parseInt(quantiteTexte.trim());

            switch (type) {
                case "AUTOMATE" -> {
                    controller.validerTexte(champMarque.getText(), "La marque");
                    int nombreES = controller.parserEntierPositif(champNombreES.getText(), "Le nombre d'entrées/sorties");
                    controller.validerTexte(champProtocole.getText(), "Le protocole réseau");
                    controller.ajouterAutomate(
                            designation.trim(), emplacement.trim(), quantite,
                            champMarque.getText().trim(), nombreES, champProtocole.getText().trim()
                    );
                }
                case "DRONE" -> {
                    double masse = controller.parserDecimalPositif(champMasse.getText(), "La masse");
                    int autonomie = controller.parserEntierPositif(champAutonomie.getText(), "L'autonomie de vol");
                    controller.validerTexte(champCapteur.getText(), "Le type de capteur");
                    controller.ajouterDrone(
                            designation.trim(), emplacement.trim(), quantite,
                            masse, autonomie, champCapteur.getText().trim()
                    );
                }
                case "DOCUMENTATION" -> {
                    controller.validerTexte(champAuteur.getText(), "L'auteur / constructeur");
                    int pages = controller.parserEntierPositif(champNombrePages.getText(), "Le nombre de pages");
                    controller.validerTexte(champLienPdf.getText(), "Le lien du manuel PDF");
                    controller.ajouterDocumentation(
                            designation.trim(), emplacement.trim(), quantite,
                            champAuteur.getText().trim(), pages, champLienPdf.getText().trim()
                    );
                }
                default -> throw new RessourceController.ValidationException("Type de ressource non reconnu.");
            }

            viderFormulaire();
            rafraichirTableau(controller.getRessources());
            appliquerFiltre();
            JOptionPane.showMessageDialog(this, "Ressource ajoutée avec succès.",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);

        } catch (RessourceController.ValidationException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement : " + e.getMessage(),
                    "Erreur base de données", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void traiterSuppression() {
        int ligneSelectionnee = tableRessources.getSelectedRow();
        if (ligneSelectionnee < 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ressource à supprimer.",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ligneModele = tableRessources.convertRowIndexToModel(ligneSelectionnee);
        int id = (int) modeleTableau.getValueAt(ligneModele, 0);
        String designation = (String) modeleTableau.getValueAt(ligneModele, 2);

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Supprimer la ressource \"" + designation + "\" (ID " + id + ") ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Ressource aSupprimer = controller.getRessources().stream()
                    .filter(r -> r.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (aSupprimer != null) {
                controller.supprimer(aSupprimer);
                rafraichirTableau(controller.getRessources());
                appliquerFiltre();
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viderFormulaire() {
        champDesignation.setText("");
        champEmplacement.setText("");
        champQuantite.setText("");
        champMarque.setText("");
        champNombreES.setText("");
        champProtocole.setText("");
        champMasse.setText("");
        champAutonomie.setText("");
        champCapteur.setText("");
        champAuteur.setText("");
        champNombrePages.setText("");
        champLienPdf.setText("");
    }

    private void rafraichirTableau(List<Ressource> ressources) {
        modeleTableau.setRowCount(0);
        for (Ressource ressource : ressources) {
            modeleTableau.addRow(new Object[]{
                    ressource.getId(),
                    ressource.getType(),
                    ressource.getDesignation(),
                    ressource.getEmplacement(),
                    ressource.getQuantite(),
                    ressource.getDiagnostic()
            });
        }
    }

    private void appliquerFiltre() {
        String critere = champRecherche.getText();

        if (critere == null || critere.isBlank()) {
            trieurTableau.setRowFilter(null);
        } else {
            trieurTableau.setRowFilter(
                    RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(critere), 1, 2, 3)
            );
        }
    }
}
