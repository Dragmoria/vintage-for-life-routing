package com.vintageforlife.client.UserManagement;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.vintageforlife.client.enums.Role;
import com.vintageforlife.client.http.HttpService;

public class CreateAccountPage {
    private final UserManagement userManagement;

    public CreateAccountPage(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    public Scene createScene(Stage stage) {
        // Voeg GUI-elementen toe
        Label nameLabel = new Label("Naam:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Wachtwoord:");
        PasswordField passwordField = new PasswordField();

        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(Role.values());
        Label roleLabel = new Label("Rol:");

        Button createButton = new Button("Create Account");
        createButton.getStyleClass().add("create-button");

        createButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            Role selectedRole = roleComboBox.getValue();

            if (selectedRole != null) {
                // Roep HttpService.createUser aan
                boolean accountCreated = HttpService.createUser(name, email, password, selectedRole.name());

                // Controleer of het account is aangemaakt
                if (accountCreated) {
                    showAlert(Alert.AlertType.INFORMATION, "Account Created", "Your account has been created successfully.");
                    // Navigeer terug naar de UserManagement-pagina
                    stage.setScene(userManagement.display(stage));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Account Creation Failed", "Unable to create account.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Role Not Selected", "Please select a role.");
            }
        });

        // Voeg GUI-elementen toe aan root
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(emailLabel, 0, 1);
        gridPane.add(emailField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(roleLabel, 0, 3);
        gridPane.add(roleComboBox, 1, 3);

        VBox root = new VBox(20);
        root.getChildren().addAll(gridPane, createButton);
        root.getStyleClass().add("create-account-root");

        // Creëer scene en stel stage in
        Scene scene = new Scene(root, 400, 400);
        scene.getStylesheets().add("/CreateAccountPage.css");
        stage.setScene(scene);
        stage.setTitle("Create Account");
        stage.show();

        return scene;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
