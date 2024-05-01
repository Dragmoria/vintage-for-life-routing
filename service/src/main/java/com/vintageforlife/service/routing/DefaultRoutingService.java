package com.vintageforlife.service.routing;

import com.vintageforlife.service.routing.algorithm.Algorithm;
import com.vintageforlife.service.routing.algorithm.geneticAlgorithm.AlgorithmResult;
import com.vintageforlife.service.routing.algorithm.geneticAlgorithm.Individual;
import com.vintageforlife.service.services.googleApi.Element;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import com.vintageforlife.service.services.googleApi.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultRoutingService implements RoutingService {
    private final Matrix matrixService;

    private final Algorithm algorithm;

    @Autowired
    public DefaultRoutingService(Matrix matrix, Algorithm algorithm) {
        this.matrixService = matrix;
        this.algorithm = algorithm;
    }

    @Override
    public Graph createGraph() {
        String[] origins = {"A", "B", "C"};
        String[] destinations = {"D", "E", "F"};

        MatrixResponse matrixResponse = matrixService.getMatrix(origins, destinations);

        return graphBuilder(matrixResponse);
    }

    @Override
    public void calculateRoute(Graph graph) {
        AlgorithmResult result = algorithm.run(graph);

        List<Individual> uniqueFitnessIndividuals = result.getGenerations().stream()
                .collect(Collectors.toMap(
                        Individual::getFitness, // key = fitness
                        individual -> individual, // value = individual itself
                        (existing, replacement) -> existing, // if key is seen again, keep the first
                        LinkedHashMap::new // preserve the original order
                ))
                .values() // get the values, which are the unique individuals
                .stream() // convert to stream
                .collect(Collectors.toList());
    }

    private Graph graphBuilder(MatrixResponse matrixResponse) {
        Graph graph = new Graph();

        // Create nodes for each address
        for (String address : matrixResponse.getOriginAddresses()) {
            graph.addNode(new Node(address));
        }
        for (String address : matrixResponse.getDestinationAddresses()) {
            if (!addressExistsInGraph(graph, address)) {
                graph.addNode(new Node(address));
            }
        }

        // Create edges for each element
        for (int i = 0; i < matrixResponse.getRows().length; i++) {
            Row row = matrixResponse.getRows()[i];
            String fromAddress = matrixResponse.getOriginAddresses()[i];
            Node fromNode = getNodeFromGraph(graph, fromAddress);

            for (int j = 0; j < row.getElements().length; j++) {
                Element element = row.getElements()[j];
                String toAddress = matrixResponse.getDestinationAddresses()[j];
                Node toNode = getNodeFromGraph(graph, toAddress);

                if (fromNode != null && toNode != null) {
                    fromNode.addEdge(new Edge(fromNode, toNode, element.getDistance().getMeter()));
                }
            }
        }

        return graph;
    }

    private static boolean addressExistsInGraph(Graph graph, String address) {
        return graph.getNodes().stream().anyMatch(node -> node.getId().equals(address));
    }

    private static Node getNodeFromGraph(Graph graph, String id) {
        return graph.getNodes().stream().filter(node -> node.getId().equals(id)).findFirst().orElse(null);
    }
}
