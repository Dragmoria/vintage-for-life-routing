package com.vintageforlife.client;

//import com.vintageforlife.client.http.CreateAccountPage;
import com.vintageforlife.client.http.HttpService;
import com.vintageforlife.client.http.RouteViewer;
import com.vintageforlife.client.homepage.Homepage;
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
        Label nameLabel = new Label("Naam:");
        Label passwordLabel = new Label("Wachtwoord:");

        // TextFields
        TextField nameField = new TextField();
        PasswordField passwordField = new PasswordField();

        // Button
        Button loginButton = new Button("Inloggen");

        // GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Adding elements to the GridPane
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Setting alignment for the GridPane elements
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameField, 1, 0);
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordField, 1, 1);
        GridPane.setConstraints(loginButton, 1, 2);

        // Handling loginButton action
        loginButton.setOnAction(event -> {
            String name = nameField.getText();
            String password = passwordField.getText();

            // Aanroepen van HttpService.validateLogin
            boolean loginSuccessful = HttpService.validateLogin(name, password);

            if (loginSuccessful) {
                // Haal de token op na een succesvolle login
                String token = HttpService.getToken();

                // Toon de Homepage
                Homepage homepage = new Homepage();
                homepage.display(stage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid name or password.");
            }
        });

        // Scene maken en toevoegen van de GridPane
        Scene scene = new Scene(grid, 400, 200);

        // Stage instellen
        stage.setScene(scene);
        stage.setTitle("Inlog scherm");
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
