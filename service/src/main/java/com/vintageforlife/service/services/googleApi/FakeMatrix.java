package com.vintageforlife.service.services.googleApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeMatrix implements Matrix {
    @Override
    public MatrixResponse getMatrix(List<String> locations) {
        ObjectMapper objectMapper = new ObjectMapper();
        MatrixResponse matrixResponse = null;

        try (InputStream inputStream = getClass().getResourceAsStream("/response.json")) {
            matrixResponse = objectMapper.readValue(inputStream, MatrixResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(matrixResponse == null) {
            throw new RuntimeException("Could not read matrix response");
        }

        MatrixResponse actualResult = new MatrixResponse();
        actualResult.setOriginAddresses(new ArrayList<>());
        actualResult.setDestinationAddresses(new ArrayList<>());
        actualResult.setRows(new ArrayList<>());

        for (String originLocation : locations) {
            int indexOfOriginLocationInOriginAddresses = matrixResponse.getOriginAddresses().indexOf(originLocation);

            Row row = new Row();
            List<Element> elements = new ArrayList<>();

            for (String destinationLocation : locations) {
                int indexOfDestinationLocationInDestinationAddresses = matrixResponse.getDestinationAddresses().indexOf(destinationLocation);

                Element element = matrixResponse.getRows().get(indexOfOriginLocationInOriginAddresses).getElements().get(indexOfDestinationLocationInDestinationAddresses);

                elements.add(element);
            }

            row.setElements(elements);

            actualResult.getRows().add(row);
            actualResult.getOriginAddresses().add(originLocation);
            actualResult.getDestinationAddresses().add(originLocation);
        }

        return actualResult;
    }
}
