package com.vintageforlife.client.homepage;

import com.vintageforlife.client.UserManagement.UserManagement;
import com.vintageforlife.client.dto.RouteDTO;
import com.vintageforlife.client.dto.UserDTO;
import com.vintageforlife.client.enums.Role;
import com.vintageforlife.client.http.HttpService;
import com.vintageforlife.client.http.RouteService;
import com.vintageforlife.client.http.RouteViewer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Homepage {
    private UserManagement userManagement;

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    public Scene display(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Label logoLabel = new Label("Vintage For Life");
        logoLabel.getStyleClass().add("label-vintage");

        // HBox voor de bovenste balk met logo en profielknop
        HBox topBar = new HBox();
        topBar.getChildren().add(logoLabel);
        topBar.setSpacing(10);

        // Profielknop
        Button profileButton = new Button();
        profileButton.getStyleClass().add("icon-button");
        profileButton.setGraphic(new Label("\uD83D\uDC64")); // Unicode voor gebruikerspictogram

        // Ingelogde gebruikersnaam
        UserDTO currentUser = HttpService.getCurrentUser();
        String username = (currentUser != null) ? currentUser.getName() : "Unknown";
        Label usernameLabel = new Label("Ingelogd als: " + username);

        HBox.setHgrow(logoLabel, Priority.ALWAYS);
        topBar.getChildren().addAll(profileButton, usernameLabel);

        // Voeg de topBar toe aan de top van de BorderPane
        root.setTop(topBar);

        // Create a button that navigates to the UserManagement page
        Button userManagementButton = new Button("User Management");
        userManagementButton.setOnAction(e -> primaryStage.setScene(userManagement.display(primaryStage)));

        // Controleer de rol van de ingelogde gebruiker
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            userManagementButton.setVisible(false); // Verberg de knop als de gebruiker geen admin is
        }

        // Voeg de UserManagement knop toe aan een VBox
        VBox topBox = new VBox(topBar, userManagementButton);
        root.setTop(topBox);

        // TableView voor het weergeven van routes
        TableView<RouteDTO> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<RouteDTO, String> routeNameColumn = new TableColumn<>("Route naam");
        routeNameColumn.setCellValueFactory(data -> new SimpleStringProperty("Route " + data.getValue().getId()));

        TableColumn<RouteDTO, Void> viewColumn = new TableColumn<>("Inzien");
        viewColumn.setCellFactory(col -> createButtonCell("Route"));

        tableView.getColumns().addAll(routeNameColumn, viewColumn);

        // Routes ophalen
        List<RouteDTO> routes = RouteService.getRoutes();
        ObservableList<RouteDTO> observableRoutes = FXCollections.observableArrayList(routes);

        // Stel de routes in de TableView in
        tableView.setItems(observableRoutes);

        // Voeg de TableView toe aan de center van de BorderPane
        root.setCenter(tableView);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Vintage For Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add("/Homepage.css");

        return scene;
    }

    private TableCell<RouteDTO, Void> createButtonCell(String text) {
        return new TableCell<>() {
            private final Button button = new Button(text);

            {
                button.setOnAction(event -> {
                    RouteDTO route = getTableView().getItems().get(getIndex());
                    startRouteViewer((Stage) getTableView().getScene().getWindow(), route.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        };
    }

    private void startRouteViewer(Stage primaryStage, int routeId) {
        // Maak een nieuwe Stage aan
        Stage routeViewerStage = new Stage();

        // Maak een instance van RouteViewer
        RouteViewer routeViewer = new RouteViewer();

        // Haal het token op
        String token = HttpService.getToken();

        // Start RouteViewer in de nieuwe Stage met het token en routeId meegegeven
        routeViewer.start(routeViewerStage, token, routeId);
    }
}
