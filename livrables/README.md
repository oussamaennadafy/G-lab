# Livrables du Projet G-Lab

Ce dossier contient les documents de rendu du projet final.

## Contenu

| Fichier | Description | Format |
|---------|-------------|--------|
| `Rapport_Conception_OO.md` / `.pdf` | Rapport complet sur la conception orientée objet | Markdown + PDF |
| `Structure_Code_Source.md` / `.pdf` | Documentation de l'arborescence et description fichier par fichier | Markdown + PDF |
| `Presentation_G-Lab.pptx` | Présentation orale du projet (13 diapositives) | PowerPoint |
| `G-Lab_Code_Source.zip` | Archive des codes sources structurés (src/, pom.xml, run.sh) | ZIP |
| `generer_presentation.py` | Script Python pour régénérer la présentation | Python |

## Codes sources

Les codes sources structurés se trouvent dans :

```
src/main/java/com/glab/
├── GLabApp.java
├── model/          (4 classes POO)
├── controller/     (logique métier)
├── database/       (JDBC / SQLite)
└── view/           (interface Swing)
```

Voir `Structure_Code_Source.md` pour le détail complet.

## Conversion en PDF (optionnel)

Pour convertir le rapport en PDF avec LibreOffice :

```bash
libreoffice --headless --convert-to pdf livrables/Rapport_Conception_OO.md
libreoffice --headless --convert-to pdf livrables/Structure_Code_Source.md
```

Ou ouvrir les fichiers `.md` dans un éditeur (VS Code, Typora) et exporter en PDF.

## Présentation vidéo (optionnel)

Pour enregistrer une vidéo de démonstration :

1. Lancer l'application : `mvn exec:java` ou `bash run.sh`
2. Enregistrer l'écran avec OBS Studio ou l'outil intégré du système
3. Montrer : ajout d'un automate, filtrage, suppression, diagnostic polymorphe
4. Durée recommandée : 3 à 5 minutes

## Régénérer la présentation

```bash
bash livrables/generer_presentation.sh
```

Le script crée automatiquement l'environnement Python et installe `python-pptx` si nécessaire.

> **Note :** `python3 livrables/generer_presentation.py` ne fonctionne pas directement
> car `python-pptx` n'est pas installé dans le Python système.
