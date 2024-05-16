//package com.vintageforlife.client.http;
//
//import java.net.URLEncoder;
//import java.util.List;
//import com.vintageforlife.client.dto.RouteStepDTO;
//import com.vintageforlife.client.dto.AddressDTO;
//
//
//public class GoogleMapsURLGenerator {
//
//    public static String generateURL(List<RouteStepDTO> routeSteps) {
//        StringBuilder urlBuilder = new StringBuilder("https://www.google.com/maps/dir/");
//
//        // Loop door alle routestappen
//        for (RouteStepDTO step : routeSteps) {
//            // Haal de coördinaten van het startpunt van de stap
//            Double startLatitude = step.getOrder().getAddress().getLatitude();
//            Double startLongitude = step.getOrder().getAddress().getLongitude();
//
//            // Voeg de coördinaten van het startpunt toe aan de URL
//            urlBuilder.append(startLatitude).append(",").append(startLongitude);
//
//            // Voeg een scheidingsteken toe tussen de start- en eindcoördinaten
//            urlBuilder.append("/");
//
//            // Haal de coördinaten van het eindpunt van de stap
//            Double endLatitude = step.getOrder().getAddress().getLatitude();
//            Double endLongitude = step.getOrder().getAddress().getLongitude();
//
//            // Voeg de coördinaten van het eindpunt toe aan de URL
//            urlBuilder.append(endLatitude).append(",").append(endLongitude);
//
//            // Voeg een scheidingsteken toe tussen de huidige en volgende stap, behalve voor de laatste stap
//            if (routeSteps.indexOf(step) != routeSteps.size() - 1) {
//                urlBuilder.append("/");
//            }
//        }
//
//        // Encodeer de URL om spaties en speciale tekens te vervangen door URL-indeling
//        return URLEncoder.encode(urlBuilder.toString(), "UTF-8");
//    }
//}
//
