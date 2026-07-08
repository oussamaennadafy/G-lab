#!/usr/bin/env bash
# Génère Presentation_G-Lab.pptx (installe python-pptx automatiquement si besoin)
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_DIR"

VENV="$PROJECT_DIR/.venv-pptx"

if [ ! -d "$VENV" ]; then
  echo "Création de l'environnement Python..."
  python3 -m venv "$VENV"
fi

if ! "$VENV/bin/python" -c "import pptx" 2>/dev/null; then
  echo "Installation de python-pptx..."
  "$VENV/bin/pip" install -q -r livrables/requirements.txt
fi

echo "Génération de la présentation..."
"$VENV/bin/python" livrables/generer_presentation.py
