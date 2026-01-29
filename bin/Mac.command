#!/bin/bash
set -euo pipefail

DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR" || exit 1

JAVAFX_PATH="javafx-sdk-25-mac/lib"
JAR_FILE="theknife-1.0-SNAPSHOT.jar"

echo "DIR: $DIR"
echo "OS: macOS"
echo "uname -m: $(uname -m)"
echo "java: $(command -v java || echo 'NON TROVATO')"
java -version || true
echo

if [ ! -d "$JAVAFX_PATH" ]; then
  echo "Errore: la directory '$JAVAFX_PATH' non esiste."
  read -r
  exit 1
fi

if [ ! -f "$JAR_FILE" ]; then
  echo "Errore: il file '$JAR_FILE' non esiste in $DIR"
  read -r
  exit 1
fi

echo "Contenuto JavaFX lib (prime righe):"
ls -1 "$JAVAFX_PATH" | head -n 20
echo

java -Dprism.verbose=true \
  --module-path "$JAVAFX_PATH" \
  --add-modules javafx.controls,javafx.fxml \
  -jar "$JAR_FILE"

EXIT_CODE=$?
echo "Exit code: $EXIT_CODE"
read -r