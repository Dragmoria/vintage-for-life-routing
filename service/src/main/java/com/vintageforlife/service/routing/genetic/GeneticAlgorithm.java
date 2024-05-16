package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.routing.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GeneticAlgorithm implements Algorithm {
    private final int populationSize = 1;

    private final double mutationRate = 0.1;

    private final Double distanceWeight = 0.5;

    private final Double trucksUsedWeight = 0.5;

    private List<Chromosome> population;

    private Problem problem;

    private Float truckWidth;

    private Float truckLength;

    private Map<OrderItemDTO, Node> itemNodeMap;

    // om te normaliseren in de fitness functie
    private int maxDistance;

    private int maxTrucksUsed;

    @Override
    public Solution solve(Problem problem, Float truckWidth, Float truckLength) {
        this.problem = problem;
        this.truckWidth = truckWidth;
        this.truckLength = truckLength;
        this.runs = 0;

        generateSomeIdeaAboutMaxDistance();
        generateSomeIdeaAboutMaxTrucksUsed();

        generateItemNodeMap();

        generateInitialPopulation();

        while (exitConditionNotMet()) {
            evaluatePopulation();
            selectParents();
            crossover();
            mutate();
        }


        return null;
    }

    private void generateSomeIdeaAboutMaxTrucksUsed() {
        List<Truck> trucks = new ArrayList<>();
        Truck currentTruck = new Truck(truckWidth, truckLength);
        trucks.add(currentTruck);

        for (Node node : problem.getGraph()) {
            OrderDTO order = node.getOrder();

            if (order == null) continue;

            for (OrderItemDTO orderItem : order.getOrderItems()) {
                if (!currentTruck.addItem(orderItem)) {
                    currentTruck = new Truck(truckWidth, truckLength);
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
            for (OrderItemDTO orderItem : node.getOrder().getOrderItems()) {
                itemNodeMap.put(orderItem, node);
            }
        }
    }

    private void generateInitialPopulation() {
        population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Node> randomNodes = new ArrayList<>(problem.getGraph());
            Collections.shuffle(randomNodes);

            Chromosome chromosome = new Chromosome(randomNodes, truckWidth, truckLength, itemNodeMap, problem.getStartAndEndNode());
            population.add(chromosome);
        }
    }

    private void evaluatePopulation() {
        for (Chromosome chromosome : population) {
            // potentially some caching check here if it helps performance. needs to be evaluated

            chromosome.calculateFitness(maxDistance, maxTrucksUsed, distanceWeight, trucksUsedWeight);
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
