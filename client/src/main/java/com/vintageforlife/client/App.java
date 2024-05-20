package com.vintageforlife.client;

import com.vintageforlife.client.UserManagement.UserManagement;
import com.vintageforlife.client.homepage.Homepage;
import com.vintageforlife.client.http.HttpService;
import com.vintageforlife.client.http.RouteViewer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Labels
        Label emailLabel = new Label("Email:");
        Label passwordLabel = new Label("Password:");

        // TextFields
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Button
        Button loginButton = new Button("Login");

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Adding elements to the GridPane
        grid.add(emailLabel, 0, 0);
        grid.add(emailField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Setting alignment for the GridPane elements
        GridPane.setConstraints(emailLabel, 0, 0);
        GridPane.setConstraints(emailField, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordField, 1, 1);
        GridPane.setConstraints(loginButton, 1, 2);

        // Maak instanties van de noodzakelijke klassen
        Homepage homepage = new Homepage();
        RouteViewer routeViewer = new RouteViewer();
        UserManagement userManagement = new UserManagement();
        HttpService httpService = new HttpService();

        // Stel de afhankelijkheden in
        homepage.setUserManagement(userManagement);
        userManagement.setHomepage(homepage);
        userManagement.setUserService(httpService);

        // Handling voor loginButton
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            // Aanroepen van HttpService.validateLogin
            boolean loginSuccessful = httpService.validateLogin(email, password);

            if (loginSuccessful) {
                // Haal de token op na een succesvolle login
                String token = httpService.getToken();

                // Toon de Homepage
                stage.setScene(homepage.display(stage));
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password.");
            }
        });

        // Scene maken en toevoegen van de GridPane
        Scene scene = new Scene(grid, 400, 200);

        // Stage instellen
        stage.setScene(scene);
        stage.setTitle("Login Screen");
        scene.getStylesheets().add("/style.css");
        stage.show();
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
