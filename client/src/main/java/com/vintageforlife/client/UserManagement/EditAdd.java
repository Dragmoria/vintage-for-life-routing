package com.vintageforlife.client.UserManagement;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.vintageforlife.client.dto.UserDTO;
import com.vintageforlife.client.http.HttpService;

public class EditAdd {
    private final HttpService userService;

    public EditAdd() {
        this.userService = new HttpService();
    }

    public Scene display(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);

        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField roleField = new TextField();
        TextField passwordField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String role = roleField.getText();
            String password = passwordField.getText();

            userService.createUser(name, email, role, password);

            // Go back to the UserManagement scene after saving
            UserManagement userManagement = new UserManagement();
            primaryStage.setScene(userManagement.display(primaryStage));
        });

        root.addRow(0, new Label("Name:"), nameField);
        root.addRow(1, new Label("Email:"), emailField);
        root.addRow(2, new Label("Role:"), roleField);
        root.addRow(3, new Label("Password:"), passwordField);
        root.addRow(4, saveButton);

        return new Scene(root, 800, 600);
    }
}