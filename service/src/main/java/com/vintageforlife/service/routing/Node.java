package com.vintageforlife.service.routing;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.OrderDTO;
import lombok.Getter;

import java.util.*;

@Getter
public class Node {
    private static int nodeCount = 0;

    private final int id;

    private final OrderDTO order;

    private final List<Edge> edges;

    private final Map<Node, Edge> edgeMap;

    private final AddressDTO addressDTO;

    public Node(OrderDTO order) {
        nodeCount++;
        this.id = nodeCount;
        this.order = order;
        this.edges = new ArrayList<>();
        this.edgeMap = new HashMap<>();
        this.addressDTO = order.getAddress();
    }

    public Node(AddressDTO addressDTO) {
        nodeCount++;
        this.id = nodeCount;
        this.order = null;
        this.edges = new ArrayList<>();
        this.edgeMap = new HashMap<>();
        this.addressDTO = addressDTO;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);

        edgeMap.put(edge.getTo(), edge);
    }

    public Integer getDistanceTo(Node node) {
        return edgeMap.get(node).getDistanceInMeters();
    }

    public Edge getShortestEdge(HashSet<Node> visitedNodes) {
        edges.sort(Comparator.comparingInt(Edge::getDistanceInMeters));

        for (Edge edge : edges) {
            if (!visitedNodes.contains(edge.getTo())) {
                return edge;
            }
        }

        return null;
    }
}
