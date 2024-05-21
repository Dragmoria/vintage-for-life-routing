package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.ProductDTO;
import com.vintageforlife.service.entity.*;
import com.vintageforlife.service.repository.*;
import com.vintageforlife.service.routing.Algorithm;
import com.vintageforlife.service.routing.Problem;
import com.vintageforlife.service.routing.Solution;
import com.vintageforlife.service.services.database.DistributionCenterService;
import com.vintageforlife.service.services.database.OrderService;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmExperiment {
    private final OrderService orderService;
    private final DistributionCenterService distributionCenterService;
    private final Matrix matrix;
    private final Algorithm algorithm;

    private final AlgorithmNodeExperimentRepository algorithmNodeExperimentRepository;
    private final AlgorithmIterationsExperimentRepository algorithmIterationsExperimentRepository;
    private final AlgorithmPopulationExperimentRepository algorithmPopulationExperimentRepository;
    private final AlgorithmWeightsExperimentRepository algorithmWeightsExperimentRepository;
    private final AlgorithmMutationRateExperimentRepository algorithmMutationRateExperimentRepository;

    @Autowired
    public AlgorithmExperiment(
            OrderService orderService,
            DistributionCenterService distributionCenterService,
            Matrix matrix,
            Algorithm algorithm,
            AlgorithmNodeExperimentRepository algorithmNodeExperimentRepository,
            AlgorithmIterationsExperimentRepository algorithmIterationsExperimentRepository,
            AlgorithmPopulationExperimentRepository algorithmPopulationExperimentRepository,
            AlgorithmWeightsExperimentRepository algorithmWeightsExperimentRepository,
            AlgorithmMutationRateExperimentRepository algorithmMutationRateExperimentRepository) {
        this.orderService = orderService;
        this.distributionCenterService = distributionCenterService;
        this.matrix = matrix;
        this.algorithm = algorithm;
        this.algorithmNodeExperimentRepository = algorithmNodeExperimentRepository;
        this.algorithmIterationsExperimentRepository = algorithmIterationsExperimentRepository;
        this.algorithmPopulationExperimentRepository = algorithmPopulationExperimentRepository;
        this.algorithmWeightsExperimentRepository = algorithmWeightsExperimentRepository;
        this.algorithmMutationRateExperimentRepository = algorithmMutationRateExperimentRepository;
    }

    public void nodesExperiment() {
        List<OrderDTO> orders = orderService.getAllOrders();

        AlgorithmSettings settings = new AlgorithmSettings();

        settings.setTruckLength(5);
        settings.setTruckWidth(2.1f);
        settings.setMutationRate(0.4);
        settings.setDistanceWeight(0.8);
        settings.setTrucksUsedWeight(0.2);
        settings.setPopulationSize(200);
        settings.setAmountOfGenerations(500);

        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

        List<AlgorithmNodeExperiment> experiments = new ArrayList<>();

        int loops = 0;

        for (int i = 5; i <= orders.size(); i++) {
            List<OrderDTO> sublist = orders.subList(0, i);

            loops++;
            System.out.println("Loop: " + loops + " Node count: " + i);

            rotateProducts(sublist, settings);

            List<AddressDTO> uniqueAddresses = getUniqueAdresses(sublist);
            List<String> formattedAddresses = getFormattedAdresses(uniqueAddresses);
            formattedAddresses.add(distributionCenterDTO.getAddress().toString());

            MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);
            Problem problem = new Problem(matrixResponse, sublist, distributionCenterDTO.getAddress());

            Solution solution = algorithm.solve(problem, settings);

            AlgorithmNodeExperiment algorithmNodeExperiment = new AlgorithmNodeExperiment();

            algorithmNodeExperiment.setNodeCount(i);
            algorithmNodeExperiment.setRuntimeInSeconds(solution.algorithmDuration());
            algorithmNodeExperiment.setFitnessValue(solution.chromosome().getFitness());
            algorithmNodeExperiment.setTotalDistance(solution.chromosome().getTotalDistance());
            algorithmNodeExperiment.setTotalTrucks(solution.chromosome().getTrucks().size());
            algorithmNodeExperiment.setUniqueChromosomeSeen(solution.uniqueChromosomesSeen());

            experiments.add(algorithmNodeExperiment);
        }

        algorithmNodeExperimentRepository.saveAll(experiments);
    }

    public void populationExperiment() {
        List<OrderDTO> orders = orderService.getAllOrders();

        AlgorithmSettings settings = new AlgorithmSettings();

        settings.setTruckLength(5);
        settings.setTruckWidth(2.1f);
        settings.setMutationRate(0.4);
        settings.setDistanceWeight(0.8);
        settings.setTrucksUsedWeight(0.2);
        settings.setAmountOfGenerations(500);

        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

        List<AlgorithmPopulationExperiment> experiments = new ArrayList<>();

        List<Integer> populationSizes = List.of(50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);

        rotateProducts(orders, settings);

        List<AddressDTO> uniqueAddresses = getUniqueAdresses(orders);
        List<String> formattedAddresses = getFormattedAdresses(uniqueAddresses);
        formattedAddresses.add(distributionCenterDTO.getAddress().toString());

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);
        Problem problem = new Problem(matrixResponse, orders, distributionCenterDTO.getAddress());

        int loops = 0;

        for (Integer populationSize: populationSizes) {
            settings.setPopulationSize(populationSize);

            loops++;
            System.out.println("Loop: " + loops + " Population size: " + populationSize);

            Solution solution = algorithm.solve(problem, settings);

            AlgorithmPopulationExperiment algorithmPopulationExperiment = new AlgorithmPopulationExperiment();
            algorithmPopulationExperiment.setPopulationSize(populationSize);
            algorithmPopulationExperiment.setRuntimeInSeconds(solution.algorithmDuration());
            algorithmPopulationExperiment.setFitnessValue(solution.chromosome().getFitness());
            algorithmPopulationExperiment.setTotalDistance(solution.chromosome().getTotalDistance());
            algorithmPopulationExperiment.setTotalTrucks(solution.chromosome().getTrucks().size());
            algorithmPopulationExperiment.setUniqueChromosomeSeen(solution.uniqueChromosomesSeen());

            experiments.add(algorithmPopulationExperiment);
        }

        algorithmPopulationExperimentRepository.saveAll(experiments);
    }

    public void iterationsExperiment() {
        List<OrderDTO> orders = orderService.getAllOrders();

        AlgorithmSettings settings = new AlgorithmSettings();

        settings.setTruckLength(5);
        settings.setTruckWidth(2.1f);
        settings.setMutationRate(0.4);
        settings.setDistanceWeight(0.8);
        settings.setTrucksUsedWeight(0.2);
        settings.setPopulationSize(200);

        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

        List<AlgorithmIterationsExperiment> experiments = new ArrayList<>();

        List<Integer> iterationCounts = List.of(50, 60, 70, 80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000);

        rotateProducts(orders, settings);

        List<AddressDTO> uniqueAddresses = getUniqueAdresses(orders);
        List<String> formattedAddresses = getFormattedAdresses(uniqueAddresses);
        formattedAddresses.add(distributionCenterDTO.getAddress().toString());

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);
        Problem problem = new Problem(matrixResponse, orders, distributionCenterDTO.getAddress());

        int loops = 0;

        for (Integer iterations: iterationCounts) {
            settings.setAmountOfGenerations(iterations);

            loops++;
            System.out.println("Loop: " + loops + " Iterations: " + iterations);

            Solution solution = algorithm.solve(problem, settings);

            AlgorithmIterationsExperiment algorithmPopulationExperiment = new AlgorithmIterationsExperiment();
            algorithmPopulationExperiment.setIterations(iterations);
            algorithmPopulationExperiment.setRuntimeInSeconds(solution.algorithmDuration());
            algorithmPopulationExperiment.setFitnessValue(solution.chromosome().getFitness());
            algorithmPopulationExperiment.setTotalDistance(solution.chromosome().getTotalDistance());
            algorithmPopulationExperiment.setTotalTrucks(solution.chromosome().getTrucks().size());
            algorithmPopulationExperiment.setUniqueChromosomeSeen(solution.uniqueChromosomesSeen());

            experiments.add(algorithmPopulationExperiment);
        }

        algorithmIterationsExperimentRepository.saveAll(experiments);
    }

    public void weightsExperiment() {
        List<OrderDTO> orders = orderService.getAllOrders();

        AlgorithmSettings settings = new AlgorithmSettings();

        settings.setTruckLength(5);
        settings.setTruckWidth(2.1f);
        settings.setPopulationSize(200);
        settings.setAmountOfGenerations(500);
        settings.setMutationRate(0.4);

        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

        List<AlgorithmWeightsExperiment> experiments = new ArrayList<>();

        rotateProducts(orders, settings);

        List<AddressDTO> uniqueAddresses = getUniqueAdresses(orders);
        List<String> formattedAddresses = getFormattedAdresses(uniqueAddresses);
        formattedAddresses.add(distributionCenterDTO.getAddress().toString());

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);
        Problem problem = new Problem(matrixResponse, orders, distributionCenterDTO.getAddress());

        List<Double> distanceWeights = List.of(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);
        List<Double> trucksWeights = List.of(1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0);

        int loops = 0;

        for (int i = 0; i < distanceWeights.size(); i++) {
            settings.setDistanceWeight(distanceWeights.get(i));
            settings.setTrucksUsedWeight(trucksWeights.get(i));

            loops++;
            System.out.println("Loop: " + loops + " Distance weight: " + settings.getDistanceWeight() + " Trucks used weight: " + settings.getTrucksUsedWeight());

            Solution solution = algorithm.solve(problem, settings);

            AlgorithmWeightsExperiment algorithmWeightsExperiment = new AlgorithmWeightsExperiment();
            algorithmWeightsExperiment.setDistanceWeight(settings.getDistanceWeight());
            algorithmWeightsExperiment.setTrucksUsedWeight(settings.getTrucksUsedWeight());
            algorithmWeightsExperiment.setRuntimeInSeconds(solution.algorithmDuration());
            algorithmWeightsExperiment.setFitnessValue(solution.chromosome().getFitness());
            algorithmWeightsExperiment.setTotalDistance(solution.chromosome().getTotalDistance());
            algorithmWeightsExperiment.setTotalTrucks(solution.chromosome().getTrucks().size());
            algorithmWeightsExperiment.setUniqueChromosomeSeen(solution.uniqueChromosomesSeen());

            experiments.add(algorithmWeightsExperiment);
        }

        algorithmWeightsExperimentRepository.saveAll(experiments);
    }

    public void mutationRateExperiment() {
        List<OrderDTO> orders = orderService.getAllOrders();

        AlgorithmSettings settings = new AlgorithmSettings();

        settings.setTruckLength(5);
        settings.setTruckWidth(2.1f);
        settings.setPopulationSize(200);
        settings.setDistanceWeight(0.9);
        settings.setTrucksUsedWeight(0.1);
        settings.setAmountOfGenerations(500);

        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

        List<AlgorithmMutationRateExperiment> experiments = new ArrayList<>();

        rotateProducts(orders, settings);

        List<AddressDTO> uniqueAddresses = getUniqueAdresses(orders);
        List<String> formattedAddresses = getFormattedAdresses(uniqueAddresses);
        formattedAddresses.add(distributionCenterDTO.getAddress().toString());

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);
        Problem problem = new Problem(matrixResponse, orders, distributionCenterDTO.getAddress());

        List<Double> mutationRates = List.of(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0);

        int loops = 0;

        for (Double mutationRate: mutationRates) {
            settings.setMutationRate(mutationRate);

            loops++;
            System.out.println("Loop: " + loops + " Mutation rate: " + mutationRate);

            Solution solution = algorithm.solve(problem, settings);

            AlgorithmMutationRateExperiment algorithmMutationRateExperiment = new AlgorithmMutationRateExperiment();
            algorithmMutationRateExperiment.setMutationRate(mutationRate);
            algorithmMutationRateExperiment.setRuntimeInSeconds(solution.algorithmDuration());
            algorithmMutationRateExperiment.setFitnessValue(solution.chromosome().getFitness());
            algorithmMutationRateExperiment.setTotalDistance(solution.chromosome().getTotalDistance());
            algorithmMutationRateExperiment.setTotalTrucks(solution.chromosome().getTrucks().size());
            algorithmMutationRateExperiment.setUniqueChromosomeSeen(solution.uniqueChromosomesSeen());

            experiments.add(algorithmMutationRateExperiment);
        }

        algorithmMutationRateExperimentRepository.saveAll(experiments);
    }

    private List<String> getFormattedAdresses(List<AddressDTO> uniqueAddresses) {
        List<String> formattedAddresses = new ArrayList<>(uniqueAddresses.stream()
                .map(AddressDTO::toString).toList());

        return formattedAddresses;
    }

    private List<AddressDTO> getUniqueAdresses(List<OrderDTO> orders) {
        return orders.stream()
                .map(OrderDTO::getAddress)
                .distinct()
                .toList();
    }

    private void rotateProducts(List<OrderDTO> orders, AlgorithmSettings algorithmSettings) {
        for (OrderDTO order : orders) {
            order.getOrderItems().forEach(orderItem -> {
                orderItem.setOrder(order);
                ProductDTO product = orderItem.getProduct();

                if (product.getDepth() > product.getHeight()) {
                    product.setDepth(product.getHeight());
                }

                if (product.getWidth() > product.getHeight()) {
                    product.setWidth(product.getHeight());
                }

                if (product.getWidth() > algorithmSettings.getTruckWidth() || product.getDepth() > algorithmSettings.getTruckLength()) {
                    throw new RuntimeException("Product is too big for truck");
                }
            });
        }
    }
}
