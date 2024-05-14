package com.vintageforlife.client.http;

import com.vintageforlife.client.dto.RouteDTO;
import com.vintageforlife.client.http.RouteService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.*;
import java.util.List;

public class RouteViewer extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Haal routes op vanuit de backend
        List<RouteDTO> routes = RouteService.getRoutes();

        // Maak een VBox om de routes weer te geven
        VBox routeBox = new VBox();
        routeBox.setSpacing(10);

        // Voeg elke route toe als een label aan de VBox
        for (RouteDTO route : routes) {
            Label routeLabel = new Label("Route ID: " + route.getId() + ", Total Distance: " + route.getTotalDistanceKm() + " km");
            routeBox.getChildren().add(routeLabel);
        }

        // Maak een scene en voeg de VBox toe
        Scene scene = new Scene(routeBox, 400, 300);

        // Stel de scene in op het podium
        primaryStage.setScene(scene);
        primaryStage.setTitle("Route Viewer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

