#!/usr/bin/env bash
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$PROJECT_DIR"

# Chercher un JDK 17+ (javac requis, pas seulement java)
find_jdk() {
  local candidate
  for candidate in \
    "/usr/lib/jvm/java-21-openjdk-amd64" \
    "/usr/lib/jvm/java-17-openjdk-amd64" \
    "/usr/lib/jvm/default-java"; do
    if [ -x "$candidate/bin/javac" ] && [ -x "$candidate/bin/java" ]; then
      echo "$candidate"
      return 0
    fi
  done

  local ext
  for ext in "$HOME"/.vscode/extensions/redhat.java-*/jre/*/ "$HOME"/.antigravity/extensions/redhat.java-*/jre/*/; do
    if [ -x "$ext/bin/javac" ] && [ -x "$ext/bin/java" ]; then
      echo "$ext"
      return 0
    fi
  done

  return 1
}

if [ -z "${JAVA_HOME:-}" ]; then
  JAVA_HOME="$(find_jdk || true)"
  if [ -n "$JAVA_HOME" ]; then
    export JAVA_HOME
  fi
fi

if [ -z "${JAVA_HOME:-}" ] || [ ! -x "$JAVA_HOME/bin/javac" ]; then
  if command -v mvn >/dev/null 2>&1; then
    echo "JDK introuvable, lancement via Maven..."
    exec mvn -q exec:java -Dexec.cleanupDaemonThreads=false
  fi
  echo "Erreur : JDK 17+ introuvable. Installez openjdk-21-jdk ou définissez JAVA_HOME."
  exit 1
fi

export PATH="$JAVA_HOME/bin:$PATH"

SQLITE_JAR="lib/sqlite-jdbc-3.45.1.0.jar"
SLF4J_API_JAR="lib/slf4j-api-2.0.12.jar"
SLF4J_SIMPLE_JAR="lib/slf4j-simple-2.0.12.jar"

download_jar() {
  local dest="$1"
  local url="$2"
  if [ ! -f "$dest" ]; then
    echo "Téléchargement de $(basename "$dest")..."
    mkdir -p lib
    curl -fL -o "$dest" "$url"
  fi
}

download_jar "$SQLITE_JAR" \
  "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.45.1.0/sqlite-jdbc-3.45.1.0.jar"
download_jar "$SLF4J_API_JAR" \
  "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.12/slf4j-api-2.0.12.jar"
download_jar "$SLF4J_SIMPLE_JAR" \
  "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.12/slf4j-simple-2.0.12.jar"

CLASSPATH="$SQLITE_JAR:$SLF4J_API_JAR:$SLF4J_SIMPLE_JAR"

mkdir -p target/classes
echo "Compilation..."
javac -encoding UTF-8 -d target/classes -cp "$CLASSPATH" \
  src/main/java/com/glab/model/*.java \
  src/main/java/com/glab/database/*.java \
  src/main/java/com/glab/controller/*.java \
  src/main/java/com/glab/view/*.java \
  src/main/java/com/glab/GLabApp.java

echo "Lancement de G-Lab..."
exec java -cp "target/classes:$CLASSPATH" com.glab.GLabApp
