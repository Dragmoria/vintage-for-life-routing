//package com.vintageforlife.client.http;
//
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//import javafx.scene.web.WebView;
//import javafx.stage.Stage;
//import com.vintageforlife.client.dto.RouteStepDTO;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.List;
//import com.vintageforlife.client.dto.AddressDTO;
//import com.vintageforlife.client.dto.RouteStepDTO;
//
//public class MapViewer {
//
//    public Scene createScene(List<RouteStepDTO> routeSteps) {
//        // Stel de basis-URL voor Google Maps in
//        String baseUrl = "https://www.google.com/maps/dir/";
//
//        // Voeg de startlocatie toe aan de URL
//        RouteStepDTO firstStep = routeSteps.get(0);
//        String startLocation = formatAddress(firstStep.getOrder().getAddress());
//        String encodedStartLocation = urlEncode(startLocation);
//        StringBuilder urlBuilder = new StringBuilder(baseUrl + encodedStartLocation);
//
//        // Voeg tussenliggende stops toe aan de URL
//        for (int i = 1; i < routeSteps.size(); i++) {
//            RouteStepDTO step = routeSteps.get(i);
//            String address = formatAddress(step.getOrder().getAddress());
//            String encodedAddress = urlEncode(address);
//            urlBuilder.append("/");
//            urlBuilder.append(encodedAddress);
//        }
//
//        // Laad de Google Maps-URL in de WebView
//        WebView webView = new WebView();
//        webView.getEngine().load(urlBuilder.toString());
//
//        // Maak een VBox om de WebView weer te geven
//        VBox mapBox = new VBox();
//        mapBox.getChildren().add(webView);
//
//        // Maak een scene en voeg de VBox toe
//        Scene scene = new Scene(mapBox, 800, 600); // Pas de afmetingen naar wens aan
//        return scene;
//    }
//
//    public void start(Stage stage, List<RouteStepDTO> routeSteps) {
//        Scene scene = createScene(routeSteps);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    private String formatAddress(AddressDTO address) {
//        return address.getStreet() + " " + address.getHouseNumber() + ", " + address.getCity();
//    }
//
//    private String urlEncode(String value) {
//        try {
//            return URLEncoder.encode(value, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//}
