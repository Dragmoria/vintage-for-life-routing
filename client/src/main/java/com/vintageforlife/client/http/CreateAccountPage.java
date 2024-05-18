//package com.vintageforlife.client.http;
//
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import com.vintageforlife.client.enums.Role;
//
//public class CreateAccountPage {
//
//    public Scene createScene(Stage stage) {
//        // Voeg GUI-elementen toe
//        Label nameLabel = new Label("Name:");
//        TextField nameField = new TextField();
//
//        Label emailLabel = new Label("Email:");
//        TextField emailField = new TextField();
//
//        Label passwordLabel = new Label("Password:");
//        PasswordField passwordField = new PasswordField();
//
//        ComboBox<Role> roleComboBox = new ComboBox<>();
//        roleComboBox.getItems().addAll(Role.values());
//        Label roleLabel = new Label("Role:");
//
//        Button createButton = new Button("Create Account");
//
//        createButton.setOnAction(event -> {
//            String name = nameField.getText();
//            String email = emailField.getText();
//            String password = passwordField.getText();
//
//            Role selectedRole = roleComboBox.getValue();
//
//            if (selectedRole != null) {
//                // Roep HttpService.createUser aan
//                boolean accountCreated = HttpService.createUser(name, email, password, selectedRole.name());
//
//                // Controleer of het account is aangemaakt
//                if (accountCreated) {
//                    showAlert(Alert.AlertType.INFORMATION, "Account Created", "Your account has been created successfully.");
//                } else {
//                    showAlert(Alert.AlertType.ERROR, "Account Creation Failed", "Unable to create account.");
//                }
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Role Not Selected", "Please select a role.");
//            }
//        });
//
//        // Voeg GUI-elementen toe aan root
//        VBox root = new VBox(10);
//        root.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, roleLabel, roleComboBox, createButton);
//
//        // Creëer scene en stel stage in
//        Scene scene = new Scene(root, 300, 300);
//        stage.setScene(scene);
//        stage.setTitle("Create Account");
//        stage.show();
//
//        return scene;
//    }
//
//    private void showAlert(Alert.AlertType type, String title, String content) {
//        Alert alert = new Alert(type);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//}
