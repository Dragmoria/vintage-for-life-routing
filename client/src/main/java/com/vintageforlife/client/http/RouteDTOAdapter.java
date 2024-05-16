//package com.vintageforlife.client.http;
//
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//import java.io.IOException;
//import com.vintageforlife.client.dto.RouteDTO;
//
//public class RouteDTOAdapter extends TypeAdapter<RouteDTO> {
//
//    @Override
//    public RouteDTO read(JsonReader reader) throws IOException {
//        RouteDTO routeDTO = new RouteDTO();
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("id")) {
//                routeDTO.setId(reader.nextInt());
//            } else if (name.equals("totalDistanceKm")) {
//                routeDTO.setTotalDistanceKm(reader.nextDouble());
//            } else if (name.equals("completed")) {
//                routeDTO.setCompleted(reader.nextBoolean());
//            } else if (name.equals("user")) {
//                // Handle deserialization of UserDTO if needed
//            } else if (name.equals("routeSteps")) {
//                // Handle deserialization of RouteStepDTO list if needed
//            } else {
//                reader.skipValue(); // Skip unknown properties
//            }
//        }
//        reader.endObject();
//        return routeDTO;
//    }
//
//    @Override
//    public void write(JsonWriter writer, RouteDTO routeDTO) throws IOException {
//        // Implement serialization logic if needed
//    }
//}
