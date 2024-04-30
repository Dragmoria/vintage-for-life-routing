package com.vintageforlife.service.routing.algorithm.geneticAlgorithm;

import com.vintageforlife.service.routing.Graph;
import com.vintageforlife.service.routing.Node;
import com.vintageforlife.service.routing.algorithm.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
public class GeneticAlgorithm implements Algorithm {
    private List<Node> nodes;

    @Value("${geneticAlgorithm.populationSize}")
    private int populationSize;

    @Value("${geneticAlgorithm.mutationRate}")
    private double mutationRate;

    @Value("${geneticAlgorithm.crossoverRate}")
    private double crossoverRate;

    @Value("${geneticAlgorithm.numberOfGenerations}")
    private int numberOfGenerations;

    @Value("${geneticAlgorithm.numberOfRandomParents}")
    private int numberOfRandomParents;

    @Value("${geneticAlgorithm.numberOfEliteParents}")
    private int numberOfEliteParents;

    private List<Individual> population;

    private List<Individual> generations;

    //    @Async
    @Override
    public AlgorithmResult run(Graph graph) {
        try {
            generations = new ArrayList<>();

            long startTime = System.nanoTime();

            this.nodes = graph.getNodes();
            generateInitialPopulation();

            int generation = 1;

            while (generation <= numberOfGenerations) {
                List<Individual> fittest = naturalSelection();

                population = newGeneration(fittest, fittest.subList(0, numberOfEliteParents));

                generation++;
            }

            population.sort(Comparator.comparingInt(Individual::getFitness));

            long endTime = System.nanoTime();

            long duration = (endTime - startTime);

            return new AlgorithmResult(duration, population.getFirst(), generations);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generateInitialPopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < this.populationSize; i++) {

            List<Node> route = new ArrayList<>(nodes.subList(1, nodes.size()));
            Collections.shuffle(route);
            route.addFirst(nodes.getFirst());
            route.add(nodes.getFirst());

            Individual individual = new Individual(route);

            population.add(individual);
        }
    }

    private void mutation(Individual individual) {
        List<Node> route = individual.getRoute();

        for (int i = 1; i < route.size() - 1; i++) {
            if (Math.random() < mutationRate) {
                int indexToSwap = getRandomIndex(route.size());

                Node temp = route.get(i);
                route.set(i, route.get(indexToSwap));
                route.set(indexToSwap, temp);
            }
        }
    }

    private int getRandomIndex(int size) {
        Random random = new Random();
        int index;

        do {
            index = 1 + random.nextInt(size - 2); // Exclude first and last index
        } while (index == size - 1);

        return index;
    }

    private List<Individual> newGeneration(List<Individual> parents, List<Individual> elites) {

        Collections.shuffle(parents);

        List<Individual> freshGeneration = new ArrayList<>(parents);

        freshGeneration.addAll(elites);

        while (freshGeneration.size() < populationSize) {
            Individual mother = elites.get(new Random().nextInt(numberOfEliteParents));
            Individual father = parents.get(new Random().nextInt(parents.size()));

            List<Individual> children = crossover(mother, father);

            for (Individual child: children) {
                mutation(child);
            }

            freshGeneration.addAll(children);
        }

        if (freshGeneration.size() > populationSize) {
            freshGeneration.subList(populationSize, freshGeneration.size()).clear();
        }

        return freshGeneration;
    }

    private List<Individual> crossover(Individual mother, Individual father) {
        if (Math.random() > crossoverRate) {
            return new ArrayList<>(Arrays.asList(mother, father));
        }

        List<Node> motherRoute = new ArrayList<>(mother.getRoute().subList(1, mother.getRoute().size() - 1));
        List<Node> fatherRoute = new ArrayList<>(father.getRoute().subList(1, father.getRoute().size() - 1));

        int crossOverPoint = new Random().nextInt(motherRoute.size() - 1);

        List<Node> sonRoute = new ArrayList<>(fatherRoute.subList(crossOverPoint, fatherRoute.size()));

        // looping through mother route
        for (int i = 0; i < crossOverPoint; i++) {
            Node motherItem = motherRoute.get(i);

            if (sonRoute.contains(motherItem)) {
                continue;
            }

            sonRoute.add(motherItem);
        }

        List<Node> daughterRoute = new ArrayList<>(motherRoute.subList(0, crossOverPoint));

        // looping through father route
        for (int i = crossOverPoint; i < fatherRoute.size(); i++) {
            Node fatherItem = fatherRoute.get(i);
            if (daughterRoute.contains(fatherItem)) {
                continue;
            }

            daughterRoute.add(fatherItem);
        }

        repairRoute(daughterRoute, fatherRoute.subList(0, crossOverPoint));
        repairRoute(sonRoute, motherRoute.subList(crossOverPoint, motherRoute.size()));

        Node firstNode = nodes.getFirst();

        sonRoute.addFirst(firstNode);
        sonRoute.add(firstNode);

        daughterRoute.addFirst(firstNode);
        daughterRoute.add(firstNode);

        return new ArrayList<>(Arrays.asList(new Individual(daughterRoute), new Individual(sonRoute)));
    }

    private void repairRoute(List<Node> toRepair, List<Node> repairWith) {
        if (toRepair.size() == nodes.size()) return;

        for (Node node: repairWith) {
            if (toRepair.contains(node)) continue;

            toRepair.add(node);
        }
    }

    private List<Individual> naturalSelection() {
        List<Individual> selectedIndividuals = new ArrayList<>();

        for (int i = 0; i < numberOfRandomParents; i++) {
            selectedIndividuals.add(rouletteWheelSelection());
        }

        population.sort(Comparator.comparingInt(Individual::getFitness));

        for (int i = 0; i < numberOfEliteParents; i++) {
            selectedIndividuals.add(population.get(i));
        }

        selectedIndividuals.sort(Comparator.comparingInt(Individual::getFitness));

        generations.add(selectedIndividuals.getFirst());

        return selectedIndividuals;
    }

    private Individual rouletteWheelSelection() {
        int totalFitness = population.stream().mapToInt(Individual::getFitness).sum();

        double randomFitness = Math.random() * totalFitness;

        double cumulativeFitness = 0.0;

        for (Individual individual : population) {
            cumulativeFitness += individual.getFitness();

            if (cumulativeFitness >= randomFitness) {
                return individual;
            }
        }

        return population.get(new Random().nextInt(population.size()));
    }
}
