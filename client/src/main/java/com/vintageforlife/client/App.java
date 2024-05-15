package com.vintageforlife.client;

import com.vintageforlife.client.http.CreateAccountPage;
import com.vintageforlife.client.http.HttpService;
import com.vintageforlife.client.http.RouteViewer;
import com.vintageforlife.client.enums.Role;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.vintageforlife.client.homepage.Homepage;
import com.vintageforlife.client.http.RouteViewer;
import com.vintageforlife.client.http.CreateAccountPage;
import com.vintageforlife.client.http.HttpService;
import javafx.application.Application;
import javafx.scene.Scene;



public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Het toevoegen van GUI-elementen
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");
//        Button viewRoutesButton = new Button("View Routes");

        Homepage homepage = new Homepage(); // Maak een instantie van de Homepage
        RouteViewer routeViewer = new RouteViewer(); // Maak een instantie van de RouteViewer

        // Handling voor loginButton
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            // Aanroepen van HttpService.validateLogin
            boolean loginSuccessful = HttpService.validateLogin(email, password);

            if (loginSuccessful) {
                // Haal de token op na een succesvolle login
                String token = HttpService.getToken();

                // Toon de Homepage
                homepage.display(stage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
            }
        });

        // Handling voor createAccountButton
        createAccountButton.setOnAction(event -> {
            // Maak een nieuwe Stage aan
            Stage createAccountStage = new Stage();

            // Maak een instance van CreateAccountPage
            CreateAccountPage createAccountPage = new CreateAccountPage();

            // Start CreateAccountPage in de nieuwe Stage
            createAccountPage.createScene(createAccountStage);
        });

        // Handling voor viewRoutesButton
        // Handling voor viewRoutesButton
//        viewRoutesButton.setOnAction(event -> {
//            // Maak een nieuwe scene met de RouteViewer
//            Scene routeScene = routeViewer.createScene();
//
//            // Stel de scene in op het podium
//            stage.setScene(routeScene);
//            stage.setTitle("Route Viewer");
//            stage.show();
//        });




        // Lay-out van de GUI-elementen met VBox
        VBox root = new VBox(10);
        root.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField, loginButton, createAccountButton);

        // Scene maken en toevoegen van de VBox
        Scene scene = new Scene(root, 300, 200);

        // Stage instellen
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        stage.show();
        scene.getStylesheets().add("/style.css");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
