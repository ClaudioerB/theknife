package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class HomeNotLoggedController {

    @FXML
    private ImageView loginImageView;

    @FXML
    private ImageView knifeImageView;

    @FXML
    private org.controlsfx.control.Rating ratingFilter;

    public HomeNotLoggedController() {
    }

    @FXML
    private void initialize() {
        String path = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/user.png";
        System.out.println("Path: " + path);
        java.io.File f = new java.io.File(path);
        System.out.println("Esiste? " + f.exists());

        if (f.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(f.toURI().toString())
            );
        }
        String knifePath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/theknife_icon.png";  
        java.io.File knifeFile = new java.io.File(knifePath);
        if (knifeFile.exists()) {       
            knifeImageView.setImage(
                    new javafx.scene.image.Image(knifeFile.toURI().toString())
            );
        }
    }

    @FXML
    private void changeLoginImage() {
        String newPath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/data/user_1.png"; 
        java.io.File newFile = new java.io.File(newPath);
        if (newFile.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(newFile.toURI().toString())
            );
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}
