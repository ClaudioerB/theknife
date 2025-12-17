package com.mycompany.theknife;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class PrimaryController {

    @FXML
    private ImageView loginImageView;

    public PrimaryController() {
    }

    @FXML
    private void initialize() {
        String path = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/Immagini/user.png";
        System.out.println("Path: " + path);
        java.io.File f = new java.io.File(path);
        System.out.println("Esiste? " + f.exists());

        if (f.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(f.toURI().toString())
            );
        }
    }

    @FXML
    private void changeLoginImage() {
        String newPath = System.getProperty("user.dir")
                + "/src/main/java/com/mycompany/theknife/Immagini/user_1.png"; 
        java.io.File newFile = new java.io.File(newPath);
        if (newFile.exists()) {
            loginImageView.setImage(
                    new javafx.scene.image.Image(newFile.toURI().toString())
            );
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
