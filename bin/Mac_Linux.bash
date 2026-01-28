DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$DIR"
if [ ! -d "javafx-sdk-25-mac/lib" ]; then
    echo "ERRORE: The directory was not found!"
    exit 1
fi
java --module-path "javafx-sdk-25-mac/lib"  \
    --add-modules javafx.controls,javafx.fxml \
    -jar "theknife-1.0-SNAPSHOT.jar"
if [ $? -ne 0 ]; then
    echo "An error occurred during startup.Si è verificato un errore durante l'avvio."
fi