package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.routing.Algorithm;
import com.vintageforlife.service.routing.Node;
import com.vintageforlife.service.routing.Problem;
import com.vintageforlife.service.routing.Solution;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class GeneticAlgorithm implements Algorithm {
    private final int populationSize = 1;

    private final double mutationRate = 0.1;

    private List<Chromosome> population;

    private Problem problem;

    private Float truckWidth;

    private Float truckLength;

    @Override
    public Solution solve(Problem problem, Float truckWidth, Float truckLength) {
        this.problem = problem;
        this.truckWidth = truckWidth;
        this.truckLength = truckLength;
        this.runs = 0;

        generateInitialPopulation();

        while (exitConditionNotMet()) {
            evaluatePopulation();
            selectParents();
            crossover();
            mutate();
        }


        return null;
    }

    private void generateInitialPopulation() {
        population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Node> randomNodes = new ArrayList<>(problem.getGraph());
            Collections.shuffle(randomNodes);

            Chromosome chromosome = new Chromosome(randomNodes, truckWidth, truckLength);
            population.add(chromosome);
        }
    }

    private void evaluatePopulation() {
        for (Chromosome chromosome : population) {
            chromosome.calculateFitness();
        }
    }

    private void selectParents() {

    }

    private void crossover() {

    }

    private void mutate() {

    }

    private Integer runs;

    private boolean exitConditionNotMet() {
        return runs++ < 2;
    }
}
