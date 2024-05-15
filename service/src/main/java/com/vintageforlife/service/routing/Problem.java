package com.vintageforlife.service.routing;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import com.vintageforlife.service.services.googleApi.Row;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Problem {
    private final List<Node> graph;

    private final Node startAndEndNode;

    private int startEndNodeIndex;

    private final List<OrderDTO> orderDTOS;

    private final MatrixResponse matrixResponse;

    public Problem(MatrixResponse matrixResponse, List<OrderDTO> orderDTOS, AddressDTO startAndEndAddress) {
        this.startAndEndNode = new Node(startAndEndAddress);
        this.orderDTOS = orderDTOS;
        this.matrixResponse = matrixResponse;

        this.graph = new ArrayList<>();

        startEndNodeIndex = matrixResponse.getOriginAddresses().indexOf(startAndEndAddress.toString());
        Row startEndRow = matrixResponse.getRows().get(startEndNodeIndex);

        for (int i = 0; i < startEndRow.getElements().size(); i++) {
            String toAddress = matrixResponse.getDestinationAddresses().get(i);

            Node toNode = getNodeFromGraph(toAddress);

            Integer distance = startEndRow.getElements().get(i).getDistance().getMeter();

            Edge edge = new Edge(startAndEndNode, toNode, distance);

            startAndEndNode.addEdge(edge);
        }

        createGraph();
    }

    private void createGraph() {
        for (OrderDTO orderDTO : orderDTOS) {
            Node node = new Node(orderDTO);
            graph.add(node);
        }

        for (Node node : graph) {
            Row row = matrixResponse.getRows().get(matrixResponse.getOriginAddresses().indexOf(node.getAddressDTO().toString()));

            for (int i = 0; i < row.getElements().size(); i++) {
                String toAddress = matrixResponse.getDestinationAddresses().get(i);

                Node toNode = getNodeFromGraph(toAddress);

                if (toNode == null) {
                    if (toAddress.equals(startAndEndNode.getAddressDTO().toString())) {
                        toNode = startAndEndNode;
                    } else {
                        continue;
                    }
                }

                Integer distance = row.getElements().get(i).getDistance().getMeter();

                Edge edge = new Edge(node, toNode, distance);

                node.addEdge(edge);
            }

//            Integer startEndNodeDistance = row.getElements().get(startEndNodeIndex).getDistance().getMeter();
//
//            Edge startEndEdge = new Edge(node, startAndEndNode, startEndNodeDistance);
//
//            node.addEdge(startEndEdge);
        }

//        for (int i = 0; i < matrixResponse.getRows().size(); i++) {
//            Row row = matrixResponse.getRows().get(i);
//
//            String fromAddress = matrixResponse.getOriginAddresses().get(i);
//
//            Node fromNode = getNodeFromGraph(fromAddress);
//
//            for (int j = 0; j < row.getElements().size(); j++) {
//                String toAddress = matrixResponse.getDestinationAddresses().get(j);
//
//                Node toNode = getNodeFromGraph(toAddress);
//
//                Integer distance = row.getElements().get(j).getDistance().getMeter();
//
//                Edge edge = new Edge(fromNode, toNode, distance);
//
//                fromNode.addEdge(edge);
//            }
//
//            Integer startEndNodeDistance = row.getElements().get(startEndNodeIndex).getDistance().getMeter();
//
//            Edge startEndEdge = new Edge(fromNode, startAndEndNode, startEndNodeDistance);
//
//            fromNode.addEdge(startEndEdge);
//        }
    }

    private Node getNodeFromGraph(String fromAddress) {
        return graph.stream()
                .filter(node -> node.getAddressDTO().toString().equals(fromAddress))
                .findFirst()
                .orElse(null);
    }
}
