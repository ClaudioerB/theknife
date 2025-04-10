package com.theknife;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Grafica extends Application {

    private final BorderPane parent = new BorderPane();

    @Override
    public void start(Stage stage) throws Exception {

        WebView webView = new WebView();
        
        webView.getEngine().load("https://www.coderscratchpad.com");

        this.parent.setCenter(webView);

        this.setupStage(stage);

    }

    private void setupStage(Stage stage) throws IOException {

        Scene scene = new Scene(this.parent, 640.0, 480.0);

        // Sets the stage title
        stage.setTitle("JavaFX WebView");

        // Sets the stage scene
        stage.setScene(scene);

        // Centers stage on screen
        stage.centerOnScreen();

        // Show stage on screen
        stage.show();

    }

}
