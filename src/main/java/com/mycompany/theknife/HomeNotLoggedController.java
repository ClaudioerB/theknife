package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class HomeNotLoggedController {

    @FXML
    private ImageView loginImageView;

    @FXML
    private ImageView knifeImageView;

    @FXML
    private org.controlsfx.control.Rating ratingFilter;

    private GestoreDataset gestoreDataset;

    @FXML
    private ListView<String> listViewRestaurants;

    public HomeNotLoggedController() {
        gestoreDataset = new GestoreDataset();
        
    }

    @FXML
    private void initialize() {
        String path = System.getProperty("user.dir")
                + "\\theknife\\src\\main\\java\\com\\mycompany\\theknife\\data\\user.png";
        System.out.println("Path: " + path);
        java.io.File f = new java.io.File(path);
        System.out.println("Esiste? " + f.exists());

        if (f.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(f.toURI().toString())
            );
        }
        String knifePath = System.getProperty("user.dir")
                + "\\theknife\\src\\main\\java\\com\\mycompany\\theknife\\data\\theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
        }
        fillListView();
    }

    @FXML
    private void changeLoginImage() {
        String newPath = System.getProperty("user.dir")
                + "\\theknife\\src\\main\\java\\com\\mycompany\\theknife\\data\\user_1.png"; 
        java.io.File newFile = new java.io.File(newPath);
        if (newFile.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(newFile.toURI().toString())
            );
        }
    }
    
    private void fillListView() {
        listViewRestaurants.getItems().clear();
        boolean checkfirst = true;
        for (String[] row : gestoreDataset.getDataSet()) {
            if (checkfirst) {
                checkfirst = false;
            }else {
                listViewRestaurants.getItems().add("Nome: "+row[0] + " - Stato: " + row[2] + " -Città: " + row[3]+ " -Prezzo:" + row[4] + " -Tipo: " + row[5] );
            }
        }       
    }
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}
