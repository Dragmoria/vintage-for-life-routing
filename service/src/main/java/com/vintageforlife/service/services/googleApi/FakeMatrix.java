package com.vintageforlife.service.services.googleApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FakeMatrix implements Matrix {
    @Override
    public MatrixResponse getMatrix(String[] origins, String[] destinations) {
        ObjectMapper objectMapper = new ObjectMapper();
        MatrixResponse matrixResponse = null;

        try (InputStream inputStream = getClass().getResourceAsStream("/response.json")) {
            matrixResponse = objectMapper.readValue(inputStream, MatrixResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrixResponse;
    }
}
