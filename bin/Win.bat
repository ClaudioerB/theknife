@echo off
REM TheKnife Launcher - struttura: theknife/bin/javafx-sdk-25-win
cd /d "%~dp0"

REM Verifica javafx
if not exist "javafx-sdk-25-win\lib\javafx.controls*.jar" (
    echo ERRORE: bin/javafx-sdk-25-win/lib non trovato!
    pause
    exit /b 1
)

REM Avvia
java --module-path "javafx-sdk-25-win\lib" ^
     --add-modules javafx.controls,javafx.fxml ^
     -jar "theknife-1.0-SNAPSHOT.jar"

if %ERRORLEVEL% NEQ 0 pause
