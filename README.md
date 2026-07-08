# G-Lab

Application Java de gestion du stock de matériel d'un laboratoire technologique universitaire.

## Technologies

- Java 17
- Swing (IHM)
- JDBC + SQLite
- Architecture MVC

## Structure du projet

```
src/main/java/com/glab/
├── GLabApp.java              # Point d'entrée
├── model/                    # Hiérarchie POO (Ressource abstraite + sous-classes)
├── database/                 # DatabaseManager + RessourceDAO (JDBC)
├── controller/               # Logique métier et validation
└── view/                     # Interface Swing (FenetrePrincipale)
```

## Types de ressources

| Type | Classe | Attributs spécifiques |
|------|--------|---------------------|
| Automate | `EquipementAutomate` | Marque, Nb E/S, Protocole réseau |
| Drone | `EquipementDrone` | Masse, Autonomie, Capteur embarqué |
| Documentation | `DocumentationTechnique` | Auteur, Nb pages, Lien PDF |

## Prérequis

- JDK 17+
- Maven 3.6+

## Compilation et exécution

```bash
# Compiler le projet
mvn compile

# Lancer l'application
mvn exec:java

# Créer un JAR exécutable
mvn package
java -jar target/g-lab-1.0.0.jar
```

La base de données SQLite (`glab_parc.db`) est créée automatiquement au premier lancement.

## Fonctionnalités

- Tableau dynamique affichant tout le stock
- Formulaire adaptatif selon le type de ressource (champs activés/désactivés)
- Recherche / filtrage en temps réel par désignation, type ou emplacement
- Validation des saisies avec messages d'erreur (`JOptionPane`)
- Ajout et suppression de ressources
- Persistance JDBC avec `PreparedStatement` et try-with-resources
