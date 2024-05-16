//package com.vintageforlife.client.http;
//
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//import java.io.IOException;
//import com.vintageforlife.client.dto.RouteDTO;
//
//public class CustomRouteDTOAdapter extends TypeAdapter<RouteDTO> {
//
//    @Override
//    public void write(JsonWriter writer, RouteDTO routeDTO) throws IOException {
//        // Implement serialization logic if needed
//    }
//
//    @Override
//    public RouteDTO read(JsonReader reader) throws IOException {
//        RouteDTO routeDTO = new RouteDTO();
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            switch (name) {
//                case "id":
//                    routeDTO.setId(reader.nextInt());
//                    break;
//                case "totalDistanceKm":
//                    routeDTO.setTotalDistanceKm(reader.nextDouble());
//                    break;
//                case "completed":
//                    routeDTO.setCompleted(reader.nextBoolean());
//                    break;
//                case "user":
//                    // Handle deserialization of UserDTO if needed
//                    break;
//                case "routeSteps":
//                    // Handle deserialization of RouteStepDTO list if needed
//                    break;
//                default:
//                    reader.skipValue(); // Skip unknown properties
//                    break;
//            }
//        }
//        reader.endObject();
//        return routeDTO;
//    }
//}
