package com.vintageforlife.client.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class HttpService {

    private static String  token;

    // Methode voor het valideren van een login
    public static boolean validateLogin(String email, String password) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        // Maak het JSON-verzoek
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        String requestJson = gson.toJson(requestBody);

        // Bouw het HTTP-verzoek
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/auth/login"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestJson))
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Login succesvol
                return true;
            } else {
                // Niet succesvolle login
                System.out.println("Login failed: " + response.body());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Methode voor het aanmaken van een gebruiker
    public static boolean createUser(String name, String email, String password, String role) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        // Maak het JSON-verzoek
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("role", role);
        String requestJson = gson.toJson(requestBody);

        // Log de JSON-inhoud die wordt verzonden
        System.out.println("Request JSON for createUser: " + requestJson);

        // Bouw het HTTP-verzoek
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/auth/register"))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(requestJson))
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            int statusCode = response.statusCode();

            // Voeg logmeldingen toe voor respons van de API
            System.out.println("Response status code for createUser: " + statusCode);
            System.out.println("Response body for createUser: " + response.body());

            if (statusCode == 200) {
                // Gebruiker succesvol aangemaakt
                return true;
            } else if (statusCode == 409) {
                // Conflict, mogelijk duplicaat e-mailadres
                System.out.println("Failed to create user: Duplicaat e-mailadres: " + response.body());
                return false;
            } else {
                // Andere fouten in het aanmaken van een gebruiker
                System.out.println("Failed to create user: " + response.body());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
