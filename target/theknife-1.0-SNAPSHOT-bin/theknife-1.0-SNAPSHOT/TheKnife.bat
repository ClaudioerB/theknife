@echo off
REM TheKnife Launcher - JavaFX 25.0.2 in ./scripts/javafx-sdk-25
set JAVAFX_PATH=%~dp0scripts\javafx-sdk-25
java --module-path "%JAVAFX_PATH%\lib" ^
     --add-modules javafx.controls,javafx.fxml ^
     -jar "%~dp0theknife-1.0-SNAPSHOT.jar" %*
if %ERRORLEVEL% NEQ 0 pause
