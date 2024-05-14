package com.vintageforlife.client.http;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.http.HttpResponse;
import com.vintageforlife.client.homepage.Homepage;


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

        // Handling voor loginButton
        // Handling voor loginButton
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            // Aanroepen van HttpService.validateLogin
            boolean loginSuccessful = HttpService.validateLogin(email, password);

            if (loginSuccessful) {
                // Haal de token op na een succesvolle login
                String token = HttpService.getToken();

                // Start de Homepage
                Homepage homepage = new Homepage();
                homepage.start(stage);
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
            createAccountPage.start(createAccountStage);
        });

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
