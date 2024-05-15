package com.vintageforlife.service;


import com.vintageforlife.service.routing.genetic.Rectangle;

import java.util.*;

public class TestSomeThings {
    private Integer truckWidth;

    private Integer truckLength;

    private List<TestRectangle> addedItems;

    private List<TestRectangle> itemsToAdd;

    private boolean[][] grid;

    public void test() {
        truckWidth = 1000;
        truckLength = 1500;

        grid = new boolean[truckWidth][truckLength];

        itemsToAdd = List.of(
                new TestRectangle(100, 700),
                new TestRectangle(200, 400),
                new TestRectangle(300, 600),
                new TestRectangle(400, 1000), // should be higher in y but it's not
                new TestRectangle(200, 400),
                new TestRectangle(300, 800),
                new TestRectangle(100, 700),
                new TestRectangle(300, 400)
        );

        addedItems = new ArrayList<>();

        for (TestRectangle item : itemsToAdd) {
            if (!addItem(item)) {
                System.out.println("No place found for item: " + item);
            }
        }
    }

    public boolean addItem(TestRectangle item) {
        int itemWidth = item.getWidth() / 10; // convert cm to cells
        int itemLength = item.getLength() / 10; // convert cm to cells

        for (int j = 0; j <= truckLength / 10 - itemLength; j++) {
            for (int i = 0; i <= truckWidth / 10 - itemWidth; i++) {
                if (isAreaFree(i, j, itemWidth, itemLength)) {
                    occupyArea(i, j, itemWidth, itemLength);
                    addedItems.add(new TestRectangle(item.getWidth(), item.getLength(), i * 10, j * 10));
                    int t = countOccupiedCells();
                    return true;
                }
            }
        }

        return false; // no suitable place found
    }

    public int countOccupiedCells() {
        int count = 0;
        for (int i = 0; i < truckWidth; i++) {
            for (int j = 0; j < truckLength; j++) {
                if (grid[i][j]) {
                    count++;
                }
            }
        }
        return count;
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
