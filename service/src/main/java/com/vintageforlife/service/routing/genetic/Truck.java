package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.dto.RouteStepDTO;
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
        float width = truckWidth * 100;
        float length = truckLength * 100;
        this.truckWidth = Math.round(width);
        this.truckLength = Math.round(length);

        grid = new boolean[this.truckWidth][this.truckLength];

        addedItems = new ArrayList<>();
        addedOrders = new ArrayList<>();
    }

    public boolean addItem(OrderItemDTO orderItemDTO) {
        Rectangle item = rectangleFromOrderItem(orderItemDTO);


        int itemWidth = item.getWidth() / 10; // convert cm to cells
        int itemLength = item.getLength() / 10; // convert cm to cells

        for (int j = 0; j <= truckLength / 10 - itemLength; j++) {
            for (int i = 0; i <= truckWidth / 10 - itemWidth; i++) {
                if (isAreaFree(i, j, itemWidth, itemLength)) {
                    occupyArea(i, j, itemWidth, itemLength);
                    addedItems.add(new Rectangle(item.getWidth(), item.getLength(), i * 10, j * 10));
                    addedOrders.add(orderItemDTO);
                    return true;
                }
            }
        }

        return false;
    }

    private Rectangle rectangleFromOrderItem(OrderItemDTO orderItemDTO) {
        int width = (int)(orderItemDTO.getProduct().getWidth() * 100);
        int length = (int)(orderItemDTO.getProduct().getDepth() * 100);

        return new Rectangle(width, length);
    }

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

    private void occupyArea(int startX, int startY, int width, int length) {
        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + length; j++) {
                grid[i][j] = true; // mark cell as occupied
            }
        }
    }
}
