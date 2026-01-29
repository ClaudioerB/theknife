#!/bin/bash

DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR" || exit 1

JAVAFX_PATH="javafx-sdk-25-mac/lib"
JAR_FILE="theknife-1.0-SNAPSHOT.jar"

if [ ! -d "$JAVAFX_PATH" ]; then
    echo "Errore: la directory '$JAVAFX_PATH' non esiste."
    echo "Premere INVIO per uscire"
    read -r
    exit 1
fi

if [ ! -f "$JAR_FILE" ]; then
    echo "Errore: il file '$JAR_FILE' non esiste nella cartella: $DIR"
    echo "Premere INVIO per uscire"
    read -r
    exit 1
fi

java --module-path "$JAVAFX_PATH" \
    --add-modules javafx.controls,javafx.fxml \
    -jar "$JAR_FILE"

EXIT_CODE=$?

if [ $EXIT_CODE -ne 0 ]; then
    echo "Si è verificato un errore durante l'avvio. Codice: $EXIT_CODE"
fi

echo
echo "Premere INVIO per uscire"
read -r
