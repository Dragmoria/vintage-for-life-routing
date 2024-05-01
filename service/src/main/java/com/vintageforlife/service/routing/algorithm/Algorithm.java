package com.vintageforlife.service.routing.algorithm;

import com.vintageforlife.service.routing.Graph;
import com.vintageforlife.service.routing.algorithm.geneticAlgorithm.AlgorithmResult;

public interface Algorithm {
    public AlgorithmResult run(Graph graph);
}
