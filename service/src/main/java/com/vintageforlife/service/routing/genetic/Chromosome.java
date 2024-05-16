package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.dto.ProductDTO;
import com.vintageforlife.service.routing.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Chromosome {
    private List<Node> nodes;

    private List<Truck> trucks;

    private Integer fitness;

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

        int distanceFitness = (int) (1 - (totalDistance / (double) maxDistance)) * 100;
        int trucksUsedFitness = (int) (1 - (trucks.size() / (double) maxTrucksUsed)) * 100;

        fitness = (int) (distanceFitness * distanceWeight + trucksUsedFitness * trucksUsedWeight);
    }

    private void calculateDistanceOfTrucks() {
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

    private void createTrucks() {
        trucks = new ArrayList<>();
        Truck currentTruck = new Truck(truckWidth, truckLength);
        trucks.add(currentTruck);

        for (Node node : nodes) {
            OrderDTO order = node.getOrder();

            for (OrderItemDTO orderItem : order.getOrderItems()) {
                if (!currentTruck.addItem(orderItem)) {
                    currentTruck = new Truck(truckWidth, truckLength);
                    trucks.add(currentTruck);
                    currentTruck.addItem(orderItem);
                }
            }
        }
    }
}
