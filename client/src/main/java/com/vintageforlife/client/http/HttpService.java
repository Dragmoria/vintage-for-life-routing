package com.vintageforlife.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.vintageforlife.client.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpService {
    private static String token;
    private static UserDTO currentUser;

    // Methode om de login te valideren en de token op te slaan
    public static boolean validateLogin(String email, String password) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // Maak het JSON-verzoek
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);
        String requestJson;
        try {
            requestJson = mapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Bouw het HTTP-verzoek
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Login succesvol, sla de token op
                String responseBody = response.body();
                JsonNode jsonNode = mapper.readTree(responseBody);
                setToken(jsonNode.get("access_token").asText()); // Parse het token correct
                System.out.println("Login successful. Token: " + token);

                // Haal de huidige gebruiker op
                fetchCurrentUser();

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

    // Methode om de opgeslagen token op te halen
    public static String getToken() {
        return token;
    }

    // Methode om de token op te slaan
    private static void setToken(String token) {
        HttpService.token = token;
    }

    // Methode om de huidige gebruiker op te halen
    public static UserDTO getCurrentUser() {
        return currentUser;
    }

    private static void fetchCurrentUser() {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/user/info"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            System.out.println("Fetching user info with token: " + token);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                currentUser = mapper.readValue(response.body(), UserDTO.class);
                System.out.println("User info fetched successfully: " + currentUser.getName());
            } else {
                System.out.println("Failed to fetch user info. Status code: " + response.statusCode() + ", Body: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getResponseBody(HttpResponse<String> response) {
        return response.body();
    }

    // Methode voor het aanmaken van een gebruiker
    public static boolean createUser(String name, String email, String password, String role) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // Maak het JSON-verzoek
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("role", role);

        String requestJson;
        try {
            requestJson = mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Log de JSON-inhoud die wordt verzonden
        System.out.println("Request JSON for createUser: " + requestJson);

        // Bouw het HTTP-verzoek
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/auth/register"))
                .header("Authorization", "Bearer " + token) // Voeg de token toe aan de header
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
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

    public static boolean updateUser(Integer id, String name, String email, String password, String role) {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // Maak het JSON-verzoek
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", id);
        requestBody.put("name", name);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("role", role);

        String requestJson;
        try {
            requestJson = mapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Log de JSON-inhoud die wordt verzonden
        System.out.println("Request JSON for updateUser: " + requestJson);

        // Bouw het HTTP-verzoek
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/userbeheer/" + id))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestJson))
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            // Voeg logmeldingen toe voor respons van de API
            System.out.println("Response status code for updateUser: " + statusCode);
            System.out.println("Response body for updateUser: " + response.body());

            if (statusCode == 200) {
                // Gebruiker succesvol bijgewerkt
                return true;
            } else {
                // Andere fouten in het bijwerken van een gebruiker
                System.out.println("Failed to update user: " + response.body());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<UserDTO> getAllUsers() {
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/userbeheer"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Succesvolle respons ontvangen, converteer naar lijst van User-objecten
                UserDTO[] usersArray = mapper.readValue(response.body(), UserDTO[].class);
                return List.of(usersArray);
            } else {
                // Niet succesvolle respons
                System.out.println("Failed to fetch users. Status code: " + statusCode);
                return List.of(); // Geef een lege lijst terug
            }
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Geef een lege lijst terug
        }
    }

    public static boolean deleteUser(Integer id) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/userbeheer/" + id))
                .header("Authorization", "Bearer " + token)
                .DELETE()
                .timeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();

            if (statusCode == 200) {
                // Gebruiker succesvol verwijderd
                return true;
            } else {
                // Andere fouten in het verwijderen van een gebruiker
                System.out.println("Failed to delete user: " + response.body());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
