@echo off
REM Vai alla cartella del progetto (una livello su)
cd /d "%~dp0.."

REM TheKnife Launcher - esegui da cartella progetto
set JAVAFX_PATH=%CD%\bin\javafx-sdk-25\lib
java --module-path "%JAVAFX_PATH%" ^
     --add-modules javafx.controls,javafx.fxml ^
     -jar "%CD%\bin\theknife-1.0-SNAPSHOT.jar" %*

if %ERRORLEVEL% NEQ 0 pause
