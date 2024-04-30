package com.vintageforlife.service.routing;

import java.util.concurrent.CompletableFuture;

public interface RoutingService {
    Graph createGraph();

    void calculateRoute(Graph graph);
}
