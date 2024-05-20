package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderItemDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Truck {
    private final int truckWidth;

    private final int truckLength;

    private final List<Rectangle> addedItems;

    private final List<OrderItemDTO> addedOrders;

    private final boolean[][] grid;

    public Truck(Float truckWidth, Float truckLength) {
        // Convert the value in meters to cm
        float width = truckWidth * 100;
        float length = truckLength * 100;
        this.truckWidth = Math.round(width);
        this.truckLength = Math.round(length);

        // Create a grid of to occupy spaces. These spaces are either true or false. True is occupied and false is free.
        grid = new boolean[this.truckWidth][this.truckLength];

        addedItems = new ArrayList<>();
        addedOrders = new ArrayList<>();
    }

    /**
     * Add an item to the truck. The item will be placed in the first available spot. If no spot is available, the item will not be added.
     * @param orderItemDTO
     * @return
     */
    public boolean addItem(OrderItemDTO orderItemDTO) {
        Rectangle item = rectangleFromOrderItem(orderItemDTO);


        int itemWidth = item.getWidth() / 10; // convert cm to cells
        int itemLength = item.getLength() / 10; // convert cm to cells

        // For every possible position in the truck, check if the item fits in that position.
        for (int j = 0; j <= truckLength / 10 - itemLength; j++) {
            for (int i = 0; i <= truckWidth / 10 - itemWidth; i++) {
                // This is checking if the square we are on is free or not.
                if (isAreaFree(i, j, itemWidth, itemLength)) {
                    // If it was add the order item to the list and occupy the area.
                    occupyArea(i, j, itemWidth, itemLength);
                    addedItems.add(new Rectangle(item.getWidth(), item.getLength(), i * 10, j * 10));
                    addedOrders.add(orderItemDTO);
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Converts an order item to a rectangle. This is easier to use in the algorithm.
     * @param orderItemDTO
     * @return
     */
    private Rectangle rectangleFromOrderItem(OrderItemDTO orderItemDTO) {
        int width = (int)(orderItemDTO.getProduct().getWidth() * 100);
        int length = (int)(orderItemDTO.getProduct().getDepth() * 100);

        return new Rectangle(width, length);
    }

    /**
     * Check if the area is free. This is done by checking if any of the cells required by the size of the item are occupied.
     * @param startX
     * @param startY
     * @param width
     * @param length
     * @return
     */
    private boolean isAreaFree(int startX, int startY, int width, int length) {
        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + length; j++) {
                if (grid[i][j]) {
                    return false; // cell is already occupied
                }
            }
        }
        return true;
    }

    /**
     * Set the squares required by a product to occupied so that no other item can be placed in its position.
     * @param startX
     * @param startY
     * @param width
     * @param length
     */
    private void occupyArea(int startX, int startY, int width, int length) {
        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + length; j++) {
                grid[i][j] = true; // mark cell as occupied
            }
        }
    }
}
