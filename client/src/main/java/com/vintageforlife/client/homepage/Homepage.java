package com.vintageforlife.client.homepage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.http.HttpResponse;



public class Homepage extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane header = new BorderPane();
        Label logoLabel = new Label("Vintage For Life");
        logoLabel.getStyleClass().add("label-vintage");
        HBox userInfo = new HBox();
        Label usernameLabel = new Label();

        // Ophalen van de gebruikersnaam nadat de gebruiker is ingelogd
//        String username = AuthenticationService.getLoggedInUsername();
//        if (username != null) {
//            usernameLabel.setText(username);
//        }

        Button loginButton = new Button("Login");
        Button routeButton = new Button("Route");
        HBox navigation = new HBox(loginButton, routeButton);

        header.setLeft(logoLabel);
        header.setCenter(userInfo);
        header.setRight(navigation);

        VBox content = new VBox();

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(content);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Vintage For Life");
        primaryStage.show();

        scene.getStylesheets().add("/Homepage.css");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
