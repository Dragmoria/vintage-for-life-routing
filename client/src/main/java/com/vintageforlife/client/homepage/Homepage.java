package com.vintageforlife.client.homepage;

import com.vintageforlife.client.http.RouteViewer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.vintageforlife.client.App;
import com.vintageforlife.client.http.HttpService;

public class Homepage {

    public Scene display(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Label logoLabel = new Label("Vintage For Life");
        logoLabel.getStyleClass().add("label-vintage");

        // GridPane voor het weergeven van de route-informatie en knoppen
        GridPane routesGrid = new GridPane();
        routesGrid.setHgap(10);
        routesGrid.setVgap(10);

        // Labels voor de route-informatie
        Label routeInfoLabel1 = new Label("Route 1 informatie");
        Label routeInfoLabel2 = new Label("Route 2 informatie");

        // Knoppen voor het inzien, wijzigen en verwijderen van de routes
        Button inzienButton1 = new Button("Inzien");
        Button wijzigButton1 = new Button("Wijzig");


        Button inzienButton2 = new Button("Inzien");
        Button wijzigButton2 = new Button("Wijzig");


        // Actie voor het inzien van Route 1
        inzienButton1.setOnAction(event -> startRouteViewer(primaryStage, 1));

        // Actie voor het inzien van Route 2
        inzienButton2.setOnAction(event -> startRouteViewer(primaryStage, 2));

        // Toevoegen van route-informatie en knoppen aan de GridPane
        routesGrid.addRow(0, new Label("Route Informatie"), new Label("Inzien"), new Label("Wijzig"));
        routesGrid.addRow(1, routeInfoLabel1, inzienButton1, wijzigButton1);
        routesGrid.addRow(2, routeInfoLabel2, inzienButton2, wijzigButton2);

        // Toevoegen van de GridPane aan de root BorderPane
        root.setTop(logoLabel);
        root.setCenter(routesGrid);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Vintage For Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.getStylesheets().add("/Homepage.css");

        return scene;
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
