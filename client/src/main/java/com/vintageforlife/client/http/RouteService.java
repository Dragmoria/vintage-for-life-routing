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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.vintageforlife.client.dto.RouteDTO;

public class RouteService {
    private static final String BASE_URL = "http://localhost:8080/api/v1/routes";

    public static List<RouteDTO> getRoutes() {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        String token = HttpService.getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCYXNAZ21haWwuY29tIiwiaWF0IjoxNzE1Njk3MjYwLCJleHAiOjE3MTU3ODM2NjB9.2EZm23YM2tW9E5PM-isSYdj5W81XnRr3OVipvNVlPaY")
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Succesvolle respons ontvangen, converteer naar lijst van Route-objecten
//                RouteDTO[] routesArray = gson.fromJson(response.body(), RouteDTO[].class);
//                return List.of(routesArray);

                ObjectMapper mapper = new ObjectMapper();
                List<RouteDTO> routes = mapper.readValue(response.body(), mapper.getTypeFactory().constructCollectionType(List.class, RouteDTO.class));
                return routes;
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