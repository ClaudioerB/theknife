package com.theknife;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Grafica extends Application {
    static String HOME = System.getProperty("user.dir");

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Knife");

        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();

        Image logoPath = new Image(Grafica.class.getResource("/grafica/logo.png").toExternalForm());
        primaryStage.getIcons().add(logoPath);

        String url = Grafica.class.getResource("/grafica/index.html").toExternalForm();
        if (url == null) {
            throw new RuntimeException("Resource not found: grafica/index.html");
        }
        engine.load(url);

        StackPane sp = new StackPane();
        sp.getChildren().add(browser);

        Scene root = new Scene(sp);

        primaryStage.setScene(root);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}