package com.vintageforlife.client.http;

import com.vintageforlife.client.dto.CustomerDTO;
import com.vintageforlife.client.dto.OrderDTO;
import com.vintageforlife.client.dto.RouteDTO;
import com.vintageforlife.client.dto.RouteStepDTO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RouteViewer {

    public Scene createScene(String token, int routeId) {
        // Haal routes op vanuit de backend met het meegegeven token
        List<RouteDTO> routes = RouteService.getRoutes();

        // Debug-uitvoer om de ontvangen routes te bekijken
        System.out.println("Received routes: " + routes);

        // Zoek de route met de gegeven ID
        Optional<RouteDTO> optionalRoute = routes.stream().filter(route -> route.getId() == routeId).findFirst();

        // Maak een BorderPane om de routes, de WebView en het zijpaneel weer te geven
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        if (optionalRoute.isPresent()) {
            RouteDTO route = optionalRoute.get();

            // Route-label toevoegen
            Label routeLabel = new Label("Route ID: " + route.getId() + ", Total Distance: " + route.getTotalDistanceKm() + " km");
            root.setTop(routeLabel);

            // Maak een nieuwe WebView
            WebView webView = new WebView();
            webView.setPrefSize(600, 400); // Pas deze afmetingen aan zoals gewenst

            // Voeg de WebView toe aan de BorderPane
            root.setCenter(webView);

            // Voeg de Google Maps-URL toe aan de WebView
            WebEngine webEngine = webView.getEngine();
            webEngine.load(generateGoogleMapsURL(route));

            // Voeg zijpaneel toe voor route-stappen en klantinformatie
            VBox sidePanel = createSidePanel(route.getRouteSteps());
            ScrollPane scrollPane = new ScrollPane(sidePanel);
            root.setRight(scrollPane);

        } else {
            // Als de route niet wordt gevonden, voeg een label toe dat de route niet is gevonden
            root.setCenter(new Label("Route not found"));
        }

        // Maak een scene en voeg de BorderPane toe
        Scene scene = new Scene(root, 1000, 600); // Pas deze afmetingen aan zoals gewenst
        return scene;
    }

    private VBox createSidePanel(List<RouteStepDTO> routeSteps) {
        VBox sidePanel = new VBox();
        sidePanel.setPadding(new Insets(10));
        sidePanel.setSpacing(10);

        // Loop door elke route-stap
        for (RouteStepDTO routeStep : routeSteps) {
            OrderDTO order = routeStep.getOrder();
            CustomerDTO customer = order != null ? order.getCustomer() : null;

            // Maak label voor route-stap
            Label routeStepLabel = new Label("Route Step ID: " + routeStep.getStepIndex());
            sidePanel.getChildren().add(routeStepLabel);

            // Toon klantinformatie, indien beschikbaar
            if (customer != null) {
                Label customerInfoLabel = new Label("Customer: " + customer.getName());
                sidePanel.getChildren().add(customerInfoLabel);
            } else {
                sidePanel.getChildren().add(new Label("Customer: N/A"));
            }

            // Toon retourstatus op een visueel aantrekkelijke manier, indien beschikbaar
            if (order != null) {
                Label retourLabel = new Label("Retour: " + (order.getRetour() ? "Yes" : "No"));
                retourLabel.getStyleClass().add(order.getRetour() ? "retour-true" : "retour-false");
                sidePanel.getChildren().add(retourLabel);
            } else {
                sidePanel.getChildren().add(new Label("Retour: N/A"));
            }
        }

        return sidePanel;
    }

    private String generateGoogleMapsURL(RouteDTO route) {
        // Neem alleen de adresgegevens van de route-stappen
        List<String> addresses = route.getRouteSteps().stream()
                .map(RouteStepDTO::getOrder)
                .filter(order -> order != null && order.getAddress() != null) // Voeg een filter toe om null-adressen te verwijderen
                .map(order -> order.getAddress().getStreet() + " " +
                        order.getAddress().getHouseNumber() + ", " +
                        order.getAddress().getCity())
                .collect(Collectors.toList());

        // Combineer de adressen in één URL voor Google Maps
        StringBuilder urlBuilder = new StringBuilder("https://www.google.com/maps/dir/");
        urlBuilder.append(String.join("/", addresses));

        return urlBuilder.toString();
    }

    public void start(Stage stage, String token, int routeId) {
        Scene scene = createScene(token, routeId);
        scene.getStylesheets().add("/RouteViewer.css");
        stage.setScene(scene);
        stage.show();
    }
}
