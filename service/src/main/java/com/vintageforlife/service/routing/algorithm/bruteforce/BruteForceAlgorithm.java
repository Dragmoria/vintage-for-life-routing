package com.vintageforlife.service.routing.algorithm.bruteforce;

import com.vintageforlife.service.routing.Graph;
import com.vintageforlife.service.routing.Node;
import com.vintageforlife.service.routing.algorithm.Algorithm;
import com.vintageforlife.service.routing.algorithm.geneticAlgorithm.AlgorithmResult;
import com.vintageforlife.service.routing.algorithm.geneticAlgorithm.Individual;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//@Component
public class BruteForceAlgorithm implements Algorithm {
    @Override
    public AlgorithmResult run(Graph graph) {
        nodes = graph.getNodes();
        long startTime = System.nanoTime();

        Individual winner = findBestRoute();

        long endTime = System.nanoTime();

        return new AlgorithmResult(endTime - startTime, findBestRoute(), null);
    }

    private List<Node> nodes;
    private Individual bestRoute;
    private int bestDistance = Integer.MAX_VALUE;

    private Individual findBestRoute() {
        permute(nodes, 1);
        return bestRoute;
    }

    private void permute(List<Node> arr, int k) {
        for (int i = k; i < arr.size(); i++) {
            Collections.swap(arr, i, k);
            permute(arr, k + 1);
            Collections.swap(arr, k, i);
        }
        if (k == arr.size() - 1) {
            Individual currentRoute = new Individual(new LinkedList<>(arr));
            int currentDistance = currentRoute.getFitness();
            if (currentDistance < bestDistance) {
                bestRoute = currentRoute;
                bestDistance = currentDistance;
            }
        }
    }
}
