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

    /**
     * Calculate the fitness of the chromosome. Every chromosome has a fitness value depending on the scores of the amount of used trucks and the total distance. More trucks used and more distance traveled will result in a lower fitness value.
     * A more detailed reasoning behind the fitness calculation can be found in the technical design document.
     * @param maxTrucksUsed
     * @param maxDistance
     * @param distanceWeight
     * @param trucksUsedWeight
     */
    public void calculateFitness(int maxTrucksUsed, int maxDistance, Double distanceWeight, Double trucksUsedWeight) {
        createTrucks();
        calculateDistanceOfTrucks();

        // Normalize the distance and trucks used to a value between 0 and 1. If the distance is less than 0, the fitness will be 0.01.
        double distanceFitness = 1 - (totalDistance / (double) maxDistance);
        if (distanceFitness < 0) {
            distanceFitness = 0.01;
        }

        // Same for the trucks used
        double trucksUsedFitness = 1 - (trucks.size() / (double) maxTrucksUsed);
        if (trucksUsedFitness < 0) {
            trucksUsedFitness = 0.01;
        }

        // Calculate the fitness value based on the weights of the distance and trucks used
        fitness = distanceFitness * distanceWeight + trucksUsedFitness * trucksUsedWeight;
    }

    /**
     * Calculate the total distance of all trucks in the chromosome. This is done by calculating the distance from the start node to the first item node, then from the first item node to the second item node, and so on. Finally, the distance from the last item node to the end node is added.
     * The total distance is the distances of all trucks combined.
     */
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

    /**
     * To create an effective route with as little trucks as possible we need to determine what orders can and cannot fit in a truck. This is done by creating trucks and adding orders to them. If an order does not fit in the truck, a new truck is created.
     * If the total travel duration of a truck exceeds 6 hours, a new truck is created. This is to prevent the driver from driving too long. The maximum travel duration is set to 6 hours. This is an arbitrary number based on the amount of hours in a day and
     * taking into account a 1-2 hour stop per customer.
     */
    public void createTrucks() {
        // Start with one truck
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

            // Check if the currently filling truck is exceeding the maximum travel time of 6 hours
            if (durationPlusEnd > 6 * 60 * 60) {
                // If that is the case, create a new truck
                currentTruck = new Truck(truckWidth, truckLength);
                trucks.add(currentTruck);
                duration = 0;
                lastNode = startAndEndNode;
            }

            // Then we try to fit the order items from the current node into the truck one by one.
            for (OrderItemDTO orderItem : order.getOrderItems()) {
                // If the order item fits in the truck it will stop and go to the next order item.
                if (!currentTruck.addItem(orderItem)) {
                    // If it does not fit, we create a new truck and add the order item to that truck. And continue to the next order item if there is any.
                    currentTruck = new Truck(truckWidth, truckLength);
                    trucks.add(currentTruck);
                    currentTruck.addItem(orderItem);
                }
            }
        }
    }

    /**
     * Creates routes based on the trucks in the chromosome. A truck is a collection of order items that need delivering in order. The route is a collection of steps that need to be taken to deliver the order items.
     * @return
     */
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
