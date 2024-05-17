package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.dto.RouteStepDTO;
import com.vintageforlife.service.routing.Node;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class Chromosome {
    private List<Node> nodes;

    private List<Truck> trucks;

    private double fitness;

    private Float truckWidth;

    private Float truckLength;

    private Map<OrderItemDTO, Node> itemNodeMap;

    private int totalDistance;

    private Node startAndEndNode;

    public Chromosome(
            List<Node> nodes,
            Float truckWidth,
            Float truckLength,
            Map<OrderItemDTO, Node> itemNodeMap,
            Node startAndEndNode) {
        this.nodes = nodes;
        this.truckWidth = truckWidth;
        this.truckLength = truckLength;
        this.itemNodeMap = itemNodeMap;
        this.startAndEndNode = startAndEndNode;
    }

    public void calculateFitness(int maxTrucksUsed, int maxDistance, Double distanceWeight, Double trucksUsedWeight) {
        createTrucks();
        calculateDistanceOfTrucks();

        double distanceFitness = 1 - (totalDistance / (double) maxDistance);
        if (distanceFitness < 0) {
            distanceFitness = 0.01;
        }

        double trucksUsedFitness = 1 - (trucks.size() / (double) maxTrucksUsed);
        if (trucksUsedFitness < 0) {
            trucksUsedFitness = 0.01;
        }

        fitness = distanceFitness * distanceWeight + trucksUsedFitness * trucksUsedWeight;
    }

    public void calculateDistanceOfTrucks() {
        totalDistance = 0;

        for (Truck truck: trucks) {
            totalDistance += startAndEndNode.getDistanceTo(itemNodeMap.get(truck.getAddedOrders().getFirst()));

            for (OrderItemDTO orderItem: truck.getAddedOrders()) {
                Node node = itemNodeMap.get(orderItem);
                totalDistance += node.getDistanceTo(node);
            }

            totalDistance += startAndEndNode.getDistanceTo(itemNodeMap.get(truck.getAddedOrders().getLast()));
        }
    }

    public void createTrucks() {
        trucks = new ArrayList<>();
        Truck currentTruck = new Truck(truckWidth, truckLength);
        trucks.add(currentTruck);

        int duration = 0;
        Node lastNode = startAndEndNode;

        for (Node node : nodes) {
            OrderDTO order = node.getOrder();

            duration += lastNode.getDurationTo(node);

            lastNode = node;

            int durationPlusEnd = duration + startAndEndNode.getDurationTo(node);

            if (durationPlusEnd > 6 * 60 * 60) {
                currentTruck = new Truck(truckWidth, truckLength);
                trucks.add(currentTruck);
                duration = 0;
                lastNode = startAndEndNode;
            }

            for (OrderItemDTO orderItem : order.getOrderItems()) {
                if (!currentTruck.addItem(orderItem)) {
                    currentTruck = new Truck(truckWidth, truckLength);
                    trucks.add(currentTruck);
                    currentTruck.addItem(orderItem);
                }
            }
        }
    }

    public List<RouteDTO> getRoutes() {
        List<RouteDTO> routes = new ArrayList<>();

        for (Truck truck: trucks) {
            RouteDTO route = new RouteDTO();
            route.setCompleted(false);
            List<RouteStepDTO> routeStepDTOS = new ArrayList<>();

            int currentStepIndex = 1;

            float totalDistanceInKm = 0.0f;

            RouteStepDTO startStep = new RouteStepDTO();
            startStep.setStepIndex(currentStepIndex);
            startStep.setRoute(route);
            startStep.setDistanceKm(0.0f);
            startStep.setCompleted(false);

            routeStepDTOS.add(startStep);

            Node lastNode = startAndEndNode;


            for (OrderItemDTO orderItemDTO: truck.getAddedOrders()) {
                currentStepIndex++;
                RouteStepDTO routeStepDTO = new RouteStepDTO();

                routeStepDTO.setRoute(route);
                routeStepDTO.setCompleted(false);
                routeStepDTO.setStepIndex(currentStepIndex);
                routeStepDTO.setOrder(orderItemDTO.getOrder());

                Node currentNode = itemNodeMap.get(orderItemDTO);
                routeStepDTO.setDistanceKm(lastNode.getDistanceTo(currentNode) / 1000f);

                routeStepDTOS.add(routeStepDTO);

                if (lastNode.getOrder() != null) {
                    if (lastNode.getOrder().equals(currentNode.getOrder())) {
                        continue;
                    }
                }

                totalDistanceInKm += routeStepDTO.getDistanceKm();

                lastNode = currentNode;
            }

            currentStepIndex++;
            RouteStepDTO endStep = new RouteStepDTO();
            endStep.setStepIndex(currentStepIndex);
            endStep.setRoute(route);
            endStep.setCompleted(false);
            endStep.setDistanceKm(lastNode.getDistanceTo(startAndEndNode) / 1000f);

            totalDistanceInKm += endStep.getDistanceKm();
            route.setTotalDistanceKm(totalDistanceInKm);

            routeStepDTOS.add(endStep);

            route.setRouteSteps(routeStepDTOS);

            routes.add(route);
        }

        return routes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Objects.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }
}
