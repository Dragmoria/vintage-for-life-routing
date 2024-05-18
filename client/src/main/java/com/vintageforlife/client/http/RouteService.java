package com.vintageforlife.client.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vintageforlife.client.dto.RouteDTO;

public class RouteService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/routes";

    public static List<RouteDTO> getRoutes() {
        HttpClient client = HttpClient.newHttpClient();

        // Haal het token op
        String token = HttpService.getToken();
        System.out.println("Using token: " + token); // Voeg deze regel toe voor debug-doeleinden

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + token) // Gebruik het opgehaalde token
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Succesvolle respons ontvangen, converteer naar lijst van Route-objecten
                ObjectMapper mapper = new ObjectMapper();
                List<RouteDTO> routes = mapper.readValue(response.body(), mapper.getTypeFactory().constructCollectionType(List.class, RouteDTO.class));
                return routes;
            } else if (statusCode == 403) {
                // Ongeautoriseerde toegang, token is mogelijk verlopen of ongeldig
                System.out.println("Unauthorized access. Please check your authentication token.");
                return new ArrayList<>(); // Geef een lege lijst terug
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
