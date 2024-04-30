package com.vintageforlife.service.routing.algorithm;

import com.vintageforlife.service.routing.Graph;
import com.vintageforlife.service.routing.algorithm.geneticAlgorithm.AlgorithmResult;

import java.util.concurrent.CompletableFuture;

public interface Algorithm {
    public AlgorithmResult run(Graph graph);
}
