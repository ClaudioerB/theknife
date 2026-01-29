package com.mycompany.theknife;

import java.io.File;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author TheKnifeTeam
 * 
 * ControllerVisualizzaRecensioneSenzaRisposta rappresenta il controller per la scena di VisualizzaRecensioneSenzaRisposta.<br>
 * Si occupa di gestire l'interfaccia di visualizzazione di una recensione senza risposta del proprietario.<br>
 * 
 * @version 1.0
 */
public class ControllerVisualizzaRecensioneSenzaRisposta {
    @FXML
    private javafx.scene.control.TextField TitoloField;
    @FXML
    private javafx.scene.control.TextArea TestoArea;
    @FXML
    private Rating ratingRecensione;
    @FXML
    private javafx.scene.control.Button inviaRecensioneButton;
    @FXML
    private ImageView theKnifeImageView;
    

    /**
     * Costruttore di ControllerVisualizzaRecensioneSenzaRisposta<br>
     */
    public ControllerVisualizzaRecensioneSenzaRisposta() {
        
    }   
    /**
     * Metodo di inizializzazione<br>
     * Inizializza il logo e disabilita la modifica ai campi di testo.<br>
     */
    public void initialize() {
        
        theKnifeImageViewSet();
        setDisableFields();
    }
    /**
     * Metodo per imponere il logo.<br>
     */
    public void theKnifeImageViewSet() {
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
       
        if (!new File(knifePath).exists()) {
            knifePath = System.getProperty("user.dir")
            + "/../src/main/java/com/mycompany/theknife/data/theknife_icon.png";
        }
            theKnifeImageView.setImage(
                new javafx.scene.image.Image(new File(knifePath).toURI().toString())
        );
        theKnifeImageView.setVisible(true); 
    }
    /**
     * Metodo per disabilitare la modifica ai campi di testo.<br>
     */
    private void setDisableFields() {
        TitoloField.setEditable(false);
        TestoArea.setEditable(false);
        ratingRecensione.setDisable(true);
    }
    /**
     * Metodo per impostare la recensione da visualizzare.<br>
     * @param recensioneDaVisualizzare recensione da visualizzare
     */
    public void setRecensione(Recensione recensioneDaVisualizzare) {
        TitoloField.setText(recensioneDaVisualizzare.getTitolo());
        TestoArea.setText(recensioneDaVisualizzare.getRecensione());
        ratingRecensione.setRating(recensioneDaVisualizzare.getStelle());
    }
    /**
     * Metodo FXML per chiudere la finestra.<br>
     */
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) TitoloField.getScene().getWindow();
        stage.close();
    }
}
