package com.vintageforlife.client.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.vintageforlife.client.dto.RouteDTO;

public class RouteService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/routes";

    // Methode om routes op te halen vanuit de backend
    public static List<RouteDTO> getRoutes() {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        // Bouw het HTTP-verzoek
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + HttpService.getToken()) // Voeg token toe aan header
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Succesvolle respons ontvangen, converteer naar lijst van Route-objecten
                RouteDTO[] routesArray = gson.fromJson(response.body(), RouteDTO[].class);
                return List.of(routesArray);
            } else {
                // Niet succesvolle respons
                System.out.println("Failed to fetch routes. Status code: " + statusCode);
                return new ArrayList<>(); // Geef een lege lijst terug
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Geef een lege lijst terug
        }
    }
}
