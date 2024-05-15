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

@Getter
@Setter
public class Chromosome {
    private List<Node> nodes;

    private List<Truck> trucks;

    private Integer fitness;

    private Double distanceWeight = 0.5;

    private Double truckCountWeight = 0.5;

    private Float truckWidth;

    private Float truckLength;

    public Chromosome(List<Node> nodes, Float truckWidth, Float truckLength) {
        this.nodes = nodes;
        this.truckWidth = truckWidth;
        this.truckLength = truckLength;
    }

    public void calculateFitness() {
        // create a list of trucks based on the items in the nodes. Every node has an order and every order has some order items.
        // Each order item has a product and that producs has a width, a height and a depth.
        // if the width or depth are bigger than the height we need to virtually rotate the product so the height is the biggest dimension
        // then we add it to the truck as a 2d item with the width and depth as the dimensions
        // then we add the next item to the truck trying to fit it in best. If it doesn't fit we add it to the next truck

        // when we have created all trucks we calculate the total distance of all the trucks
        // then we combine the total distance and the amount of trucks to create a fitness value

        // the fitness value should be higher the less distance and the less trucks we use

        trucks = new ArrayList<>();
        Truck currentTruck = new Truck(truckWidth, truckLength);
        trucks.add(currentTruck);

        for (Node node : nodes) {
            OrderDTO order = node.getOrder();

            for (OrderItemDTO orderItem : order.getOrderItems()) {
                ProductDTO product = orderItem.getProduct();

                if (product.getDepth() > product.getHeight()) {
                    product.setDepth(product.getHeight());
                }

                if (product.getWidth() > product.getHeight()) {
                    product.setWidth(product.getHeight());
                }

                if (product.getWidth() > truckWidth || product.getDepth() > truckLength) {
                    throw new RuntimeException("Product is too big for truck");
                }

                if (!currentTruck.addItem(orderItem)) {
                    currentTruck = new Truck(truckWidth, truckLength);
                    trucks.add(currentTruck);
                    currentTruck.addItem(orderItem);
                }
            }
        }
    }
}
