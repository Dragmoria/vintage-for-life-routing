package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.routing.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GeneticAlgorithm implements Algorithm {
    /**
     * The population of chromosomes, these are possible combinations of nodes from our graph. Each chromosome represents a possible solution to the problem.
     */
    private List<Chromosome> population;

    /**
     * The problem we are trying to solve.
     */
    private Problem problem;

    /**
     * The settings for the algorithm. This object holds certain settings like the population size, the mutation rate, the amount of generations, etc.
     */
    private AlgorithmSettings settings;

    /**
     * A map that maps an order item to a node in the graph. This is used to quickly find the node that belongs to an order item. This helps us with performance when calculating the fitness value.
     */
    private Map<OrderItemDTO, Node> itemNodeMap;

    /**
     * The amount of generations that have passed. This is used to check if the exit condition is met.
     */
    private Integer generations;

    /**
     * The maximum distance that a chromosome has traveled. This is used to calculate the fitness value. And is used as a normalization factor. This value is updated every time a new maximum distance is found in a generation.
     */
    private int maxDistance;

    /**
     * The maximum amount of trucks that have been used in a chromosome. This is used to calculate the fitness value. And is used as a normalization factor. This value is updated every time a new maximum amount of trucks used is found in a generation.
     */
    private int maxTrucksUsed;

    /**
     * The main method in a sense. This is what can be called to start the algorithm. It will return a solution record.
     * @param problem The problem to solve.
     * @param settings Algorithm settings retrieved from the database.
     * @return The solution record created based on the fittest found chromosome over x generations.
     */
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

        // Main loop of the algorithm. This loop will run until the exit condition is met.
        while (exitConditionNotMet()) {
            int notFound = 0;

            for (Chromosome chromosome : population) {
                if(!allTriedChromosomes.contains(chromosome.hashCode())) {
                    notFound++;
                }
                allTriedChromosomes.add(chromosome.hashCode());
            }

            double percentageNotFound = (double) notFound / population.size() * 100;

            System.out.println("Percentage not found: " + percentageNotFound);


            evaluatePopulation();
            checkForMaximums();
            crossover();
        }

        Chromosome bestFoundChromosome = population.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).orElseThrow(() -> new RuntimeException("No solution found"));

        long endTime = System.nanoTime();

        return new Solution((endTime - startTime) / 1000000, bestFoundChromosome, allTriedChromosomes.size());
    }

    /**
     * Every iteration of the main loop we check to see if in the current population there are maximum values that are higher than our current maximum values. If so, we update the maximum values. This should over time stabilize the algorithms fitness function.
     */
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

    /**
     * This function will give us an initial value for the maximum amount of trucks. This value is used for the calculation of our fitness values. It is not a perfect value, but it is a good estimate.
     */
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

    /**
     * This function will give us an initial value for the maximum distance. This value is used for the calculation of our fitness values. It is not a perfect value, but it is a good estimate.
     */
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

    /**
     * This function will generate a map that maps an order item to a node in the graph. This is used to quickly find the node that belongs to an order item. This helps us with performance when calculating the fitness value.
     */
    private void generateItemNodeMap() {
        itemNodeMap = new HashMap<>();
        for (Node node : problem.getGraph()) {
            if (node.getOrder() == null) continue;

            for (OrderItemDTO orderItem : node.getOrder().getOrderItems()) {
                itemNodeMap.put(orderItem, node);
            }
        }
    }

    /**
     * This function will generate the initial population of chromosomes. This is done by creating a random permutation of the nodes in the graph. This is done for the amount of the population size.
     */
    private void generateInitialPopulation() {
        population = new ArrayList<>();

        for (int i = 0; i < settings.getPopulationSize(); i++) {
            List<Node> randomNodes = new ArrayList<>(problem.getGraph());
            Collections.shuffle(randomNodes);

            Chromosome chromosome = new Chromosome(randomNodes, settings.getTruckWidth(), settings.getTruckLength(), itemNodeMap, problem.getStartAndEndNode());
            population.add(chromosome);
        }
    }

    /**
     * This function will make our chromosomes calculate their fitness values. This is done in parallel to speed up the process since it is done for the entire population.
     */
    private void evaluatePopulation() {
        population.parallelStream().forEach(chromosome -> chromosome.calculateFitness(maxTrucksUsed, maxDistance, settings.getDistanceWeight(), settings.getTrucksUsedWeight()));
    }

    /**
     * This function will select two parents from the population based on their fitness values. The fitter a chromosome is, the more likely it is to be selected as a parent.
     * @return A chromosome that is selected as a parent.
     */
    private Chromosome selectParentsRoulette() {
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

    private Chromosome selectParentsTournament() {
        int tournamentSize = 5;
        List<Chromosome> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            tournament.add(population.get(new Random().nextInt(population.size())));
        }
        return tournament.stream().max(Comparator.comparingDouble(Chromosome::getFitness)).orElseThrow(() -> new RuntimeException("No solution found"));
    }

    private List<Chromosome> selectAmountOfOldGeneration(int amount, List<Chromosome> population) {
        List<Chromosome> newPopulation = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            newPopulation.add(population.get(i));
        }
        return newPopulation;
    }

    /**
     * This function will cross two selected parents with each other. It will create two children that share some genetic information with their parents. This is done until the population is filled again.
     */
    private void crossover() {
        List<Chromosome> newPopulation = new ArrayList<>();

        // Sorts the population based on their fitness values.
        population.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());

        // Elitism, the best chromosomes from the previous generation is always added to the new generation.
        newPopulation.addAll(selectAmountOfOldGeneration(10, population));
        System.out.println(population.getFirst().getTotalDistance());

        int totalNodes = problem.getGraph().size();

        while (newPopulation.size() < settings.getPopulationSize()) {
            Chromosome parent1 = selectParentsTournament();
            Chromosome parent2 = selectParentsRoulette();

            // Prevent inbreeding
            while (parent1.equals(parent2)) {
                parent2 = selectParentsTournament();
            }

            // selects a random crossover point
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

    /**
     * This function will create a child based on two parents. The child will have some genetic information from both parents. This is done by taking the genetic information from parent1 until the crossover point.
     * Then the child will take the genetic information from parent2 past the crossover point. If the child does not have all the genetic information yet, it will take the genetic information from parent2 until the crossover point.
     * Further information about this with a visual representation can be found in the report.
     * @param totalNodes The total amount of nodes in the graph. Used to determine if the generated child has all available nodes in its genetic information.
     * @param parent1TillCrossover The genetic information from parent1 until the crossover point.
     * @param parent2TillCrossover The genetic information from parent2 until the crossover point.
     * @param parent2PastCrossover The genetic information from parent2 past the crossover point.
     * @return A child chromosome that has genetic information from both parents.
     */
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

    /**
     * This function will mutate a chromosome. This is done by swapping two random nodes in the chromosome. This is done based on the mutation rate. If the mutation rate is 0.4, there is a 40% chance that a chromosome will be mutated.
     * @param chromosome The chromosome to mutate.
     * @return The mutated chromosome.
     */
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

    /**
     * This function will check if the exit condition is met. The exit condition is met when the amount of generations is equal to the amount of generations that the algorithm should run for.
     * @return True if the exit condition is not met, false if the exit condition is met.
     */
    private boolean exitConditionNotMet() {
//        System.out.println("Generation: " + generations);
        return generations++ < settings.getAmountOfGenerations();
    }
}
