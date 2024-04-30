package com.vintageforlife.service.routing.algorithm.geneticAlgorithm;

import com.vintageforlife.service.routing.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Individual {
    private final List<Node> route;

    private int fitness;

    public Individual(List<Node> route) {
        this.route = route;

        calculateFitness();
    }

    private void calculateFitness() {
        Node previousNode = route.getFirst();

        for (int i = 1; i < route.size(); i++) {
            Node currentNode = route.get(i);

            fitness += previousNode.findDistanceTo(currentNode);
            previousNode = currentNode;
        }
    }

    public void mutate(double mutationRate) {

    }
}
