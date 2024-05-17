package com.vintageforlife.service.routing;

import lombok.Getter;

@Getter
public class Edge {
    private Node from;

    private Node to;

    private Integer distanceInMeters;

    private Integer durationInSeconds;

    public Edge(Node from, Node to, Integer distanceInMeters, Integer durationInSeconds) {
        this.from = from;
        this.to = to;
        this.distanceInMeters = distanceInMeters;
        this.durationInSeconds = durationInSeconds;
    }
}
