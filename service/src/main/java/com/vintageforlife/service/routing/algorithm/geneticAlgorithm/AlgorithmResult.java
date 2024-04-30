package com.vintageforlife.service.routing.algorithm.geneticAlgorithm;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlgorithmResult {
    private long timeSpentCalculating;

    private Individual bestIndividualFound;

    private List<Individual> generations;

    public AlgorithmResult(long timeSpentCalculating, Individual bestIndividualFound, List<Individual> generations) {
        this.timeSpentCalculating = timeSpentCalculating;
        this.bestIndividualFound = bestIndividualFound;
        this.generations = generations;
    }
}
