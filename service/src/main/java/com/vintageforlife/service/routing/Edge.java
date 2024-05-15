package com.vintageforlife.service.routing;

import lombok.Getter;

@Getter
public class Edge {
    private Node from;

    private Node to;

    private Integer distanceInMeters;

    public Edge(Node from, Node to, Integer distanceInMeters) {
        this.from = from;
        this.to = to;
        this.distanceInMeters = distanceInMeters;
    }
}
