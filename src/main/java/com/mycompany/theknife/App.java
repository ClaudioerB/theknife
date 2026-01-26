package com.mycompany.theknife;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * JavaFX App è la classe principale che rappresenta l'applicazione JavaFX e ne gestisce il ciclo di vita.<br>
 * Si occupa di inizializzare il Gestore principale e di caricare la scena iniziale dell'interfaccia utente.<br>
 * @version 1.0
 */
public class App extends Application {

    private static Scene scene;
    private static Gestore gestore;

    /**
     * Metodo che inizializza il Gestore principale e carica la scena iniziale dell'interfaccia utente.<br>
     * @param stage la finestra principale dell'applicazione
     */
    @Override
    public void start(Stage stage) throws IOException {
        gestore = Gestore.getGestore();
        scene = new Scene(loadFXML("HomeNotLogged"), 1920, 1080);
        stage.setScene(scene);
        stage.show();
    // stage.setFullScreen(true);  // ← AGGIUNGI QUESTA RIGA
    }

    /**
     * Metodo che cambia la scena dell'interfaccia utente.<br>
     * @param fxml il nome del file FXML da caricare
     */
    static public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Metodo che carica un file FXML e ne restituisce l'oggetto Parent.<br>
     * @param fxml il nome del file FXML da caricare
     * @return l'oggetto Parent caricato
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Metodo principale per avviare l'applicazione JavaFX.<br>
     * @param args argomenti da linea di comando
     */
    public static void main(String[] args) {
        launch();
    }

}