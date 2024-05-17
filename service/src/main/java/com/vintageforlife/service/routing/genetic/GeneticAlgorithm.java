package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.routing.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GeneticAlgorithm implements Algorithm {
    private List<Chromosome> population;

    private Problem problem;

    private AlgorithmSettings settings;

    private Map<OrderItemDTO, Node> itemNodeMap;

    private Integer generations;

    // om te normaliseren in de fitness functie
    private int maxDistance;

    private int maxTrucksUsed;

    @Override
    public Solution solve(Problem problem, AlgorithmSettings settings) {
        this.problem = problem;
        this.settings = settings;
        this.generations = 0;

        long startTime = System.nanoTime();

        generateSomeIdeaAboutMaxDistance();
        generateSomeIdeaAboutMaxTrucksUsed();

        generateItemNodeMap();

        generateInitialPopulation();

        HashSet<Integer> allTriedChromosomes = new HashSet<>();

        while (exitConditionNotMet()) {
            for (Chromosome chromosome : population) {
                allTriedChromosomes.add(chromosome.hashCode());
            }
            evaluatePopulation();
            checkForMaximums();
            crossover();
        }

        Chromosome bestFoundChromosome = population.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).orElse(null);

        long endTime = System.nanoTime();

        return new Solution((endTime - startTime) / 1000000, bestFoundChromosome, allTriedChromosomes.size());
    }

    private void checkForMaximums() {
        for (Chromosome chromosome : population) {
            if (chromosome.getTotalDistance() > maxDistance) {
                maxDistance = chromosome.getTotalDistance();
            }

            if (chromosome.getTrucks().size() > maxTrucksUsed) {
                maxTrucksUsed = chromosome.getTrucks().size();
            }
        }
    }

    private void generateSomeIdeaAboutMaxTrucksUsed() {
        List<Truck> trucks = new ArrayList<>();
        Truck currentTruck = new Truck(settings.getTruckWidth(), settings.getTruckLength());
        trucks.add(currentTruck);

        for (Node node : problem.getGraph()) {
            OrderDTO order = node.getOrder();

            if (order == null) continue;

            for (OrderItemDTO orderItem : order.getOrderItems()) {
                if (!currentTruck.addItem(orderItem)) {
                    currentTruck = new Truck(settings.getTruckWidth(), settings.getTruckLength());
                    trucks.add(currentTruck);
                    currentTruck.addItem(orderItem);
                }
            }
        }

        maxTrucksUsed = trucks.size();
    }

    private void generateSomeIdeaAboutMaxDistance() {
        HashSet<Node> visitedNodes = new HashSet<>();

        Node currentNode = problem.getStartAndEndNode();

        while (visitedNodes.size() < problem.getGraph().size()) {
            visitedNodes.add(currentNode);

            Edge shortestEdge = currentNode.getShortestEdge(visitedNodes);

            if (shortestEdge == null) {
                break;
            }

            maxDistance += shortestEdge.getDistanceInMeters();

            currentNode = shortestEdge.getTo();
        }

        maxDistance += currentNode.getDistanceTo(problem.getStartAndEndNode());
    }

    private void generateItemNodeMap() {
        itemNodeMap = new HashMap<>();
        for (Node node : problem.getGraph()) {
            if (node.getOrder() == null) continue;

            for (OrderItemDTO orderItem : node.getOrder().getOrderItems()) {
                itemNodeMap.put(orderItem, node);
            }
        }
    }

    private void generateInitialPopulation() {
        population = new ArrayList<>();

        for (int i = 0; i < settings.getPopulationSize(); i++) {
            List<Node> randomNodes = new ArrayList<>(problem.getGraph());
            Collections.shuffle(randomNodes);

            Chromosome chromosome = new Chromosome(randomNodes, settings.getTruckWidth(), settings.getTruckLength(), itemNodeMap, problem.getStartAndEndNode());
            population.add(chromosome);
        }
    }

    private void evaluatePopulation() {
        for (Chromosome chromosome : population) {
            chromosome.calculateFitness(maxTrucksUsed, maxDistance, settings.getDistanceWeight(), settings.getTrucksUsedWeight());
        }
    }

    private Chromosome selectParents() {
        double totalFitness = population.stream().mapToDouble(Chromosome::getFitness).sum();

        double randomFitness = new Random().nextDouble() * totalFitness;

        double currentFitness = 0;

        for (Chromosome chromosome : population) {
            currentFitness += chromosome.getFitness();

            if (currentFitness >= randomFitness) {
                return chromosome;
            }
        }

        return population.getLast();
    }

    private void crossover() {
        List<Chromosome> newPopulation = new ArrayList<>();

        population.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());

        newPopulation.add(population.getFirst());

        int totalNodes = problem.getGraph().size();

        while (newPopulation.size() < settings.getPopulationSize()) {
            Chromosome parent1 = selectParents();
            Chromosome parent2 = selectParents();

            int crossoverPoint = new Random().nextInt(totalNodes);

            List<Node> parent1TillCrossover = new ArrayList<>(parent1.getNodes().subList(0, crossoverPoint));
            List<Node> parent1PastCrossover = new ArrayList<>(parent1.getNodes().subList(crossoverPoint, totalNodes));
            List<Node> parent2TillCrossover = new ArrayList<>(parent2.getNodes().subList(0, crossoverPoint));
            List<Node> parent2PastCrossover = new ArrayList<>(parent2.getNodes().subList(crossoverPoint, totalNodes));

            newPopulation.add(mutate(createChild(totalNodes, parent1TillCrossover, parent2TillCrossover, parent2PastCrossover)));
            newPopulation.add(mutate(createChild(totalNodes, parent2TillCrossover, parent1TillCrossover, parent1PastCrossover)));
        }

        population = newPopulation;
    }

    private Chromosome createChild(int totalNodes, List<Node> parent1TillCrossover, List<Node> parent2TillCrossover, List<Node> parent2PastCrossover) {
        List<Node> childNodes = new ArrayList<>(parent1TillCrossover);

        for (Node node: parent2PastCrossover) {
            if (!childNodes.contains(node)) {
                childNodes.add(node);
            }
        }

        if (childNodes.size() < totalNodes) {
            for (Node node: parent2TillCrossover) {
                if (!childNodes.contains(node)) {
                    childNodes.add(node);
                }
            }
        }

        return new Chromosome(childNodes, settings.getTruckWidth(), settings.getTruckLength(), itemNodeMap, problem.getStartAndEndNode());
    }

    private Chromosome mutate(Chromosome chromosome) {
        if (new Random().nextDouble() < settings.getMutationRate()) {
            int index1 = new Random().nextInt(problem.getGraph().size());
            int index2 = new Random().nextInt(problem.getGraph().size());

            Node node1 = chromosome.getNodes().get(index1);
            Node node2 = chromosome.getNodes().get(index2);

            chromosome.getNodes().set(index1, node2);
            chromosome.getNodes().set(index2, node1);
        }

        return chromosome;
    }

    private boolean exitConditionNotMet() {
        return generations++ < settings.getAmountOfGenerations();
    }
}
