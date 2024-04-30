package com.vintageforlife.service.routing;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Node {
    private final String id;

    private final List<Edge> edges;

    private final Map<Node, Edge> edgeMap;

    public Node(String id) {
        this.id = id;
        this.edges = new ArrayList<>();
        edgeMap = new HashMap<>();
    }

    public void addEdge(Edge edge) {
        edges.add(edge);

        edgeMap.put(edge.getTo(), edge);
    }

    public int findDistanceTo(Node targetNode) {
        return edgeMap.get(targetNode).getWeight();
    }
}
