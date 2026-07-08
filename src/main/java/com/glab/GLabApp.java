package com.glab;

import com.glab.controller.RessourceController;
import com.glab.database.DatabaseManager;
import com.glab.view.FenetrePrincipale;

import javax.swing.UIManager;

/**
 * Point d'entrée de l'application G-Lab.
 */
public class GLabApp {

    public static void main(String[] args) {
        DatabaseManager.initialiserBase();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Conserver le look par défaut si le L&F système n'est pas disponible
        }

        java.awt.EventQueue.invokeLater(() -> {
            RessourceController controller = new RessourceController();
            new FenetrePrincipale(controller).setVisible(true);
        });
    }
}
