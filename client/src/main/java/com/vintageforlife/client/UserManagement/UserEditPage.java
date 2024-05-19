package com.vintageforlife.client.UserManagement;

import com.vintageforlife.client.dto.UserDTO;
import com.vintageforlife.client.enums.Role;
import com.vintageforlife.client.http.HttpService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


public class UserEditPage {

    private final HttpService userService;

    public UserEditPage(HttpService userService) {
        this.userService = userService;
    }

    public Scene display(Stage primaryStage, UserDTO user) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label nameLabel = new Label("Naam:");
        TextField nameField = new TextField(user.getName());

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(user.getEmail());

        Label passwordLabel = new Label("Wachtwoord:");
        PasswordField passwordField = new PasswordField();
        passwordField.setText(user.getPassword());

        Label roleLabel = new Label("Rol:");
        ComboBox<Role> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(Role.values());
        roleComboBox.setValue(user.getRole());

        Button saveButton = new Button("Opslaan");
        saveButton.getStyleClass().add("save-button");

        saveButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Role role = roleComboBox.getValue();

            boolean success = userService.updateUser(user.getId(), name, email, password, role.name());
            if (success) {
                // Ga terug naar UserManagement pagina na succesvolle update
                UserManagement userManagement = new UserManagement();
                userManagement.setUserService(userService);
                Scene userManagementScene = userManagement.display(primaryStage);
                primaryStage.setScene(userManagementScene);
            } else {
                // Toon een foutmelding bij mislukte update
                showAlert(Alert.AlertType.ERROR, "Update Failed", "Unable to update user.");
            }
        });

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(emailLabel, 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(roleLabel, 0, 3);
        grid.add(roleComboBox, 1, 3);
        grid.add(saveButton, 1, 4);

        VBox vbox = new VBox(grid);
        vbox.setSpacing(20);
        vbox.getStyleClass().add("edit-account-root");

        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add("/UserEditPage.css");

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
