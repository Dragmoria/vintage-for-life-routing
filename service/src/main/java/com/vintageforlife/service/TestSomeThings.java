package com.vintageforlife.service;


import com.vintageforlife.service.routing.genetic.Rectangle;

import java.util.List;

public class TestSomeThings {
    private Integer truckWidth;

    private Integer truckLength;

    private List<TestRectangle> addedItems;

    private List<TestRectangle> itemsToAdd;

    private List<TestRectangle> remainingSpaces;

    public void test() {
        truckWidth = 10;
        truckLength = 15;

        itemsToAdd = List.of(
                new TestRectangle(1, 7),
                new TestRectangle(2, 4),
                new TestRectangle(3, 6),
                new TestRectangle(4, 10),
                new TestRectangle(2, 4),
                new TestRectangle(3, 8),
                new TestRectangle(1, 7),
                new TestRectangle(3, 4)
        );

        remainingSpaces = List.of(
                new TestRectangle(truckWidth, truckLength, 0, 0),
                new TestRectangle(truckWidth, truckLength, 0, 0)
        );
    }

    public void tryToAdd() {
        for (TestRectangle item: itemsToAdd) {
            for (TestRectangle remainingSpace: remainingSpaces) {
                remainingSpace.canFit(item);
            }
        }
    }
}
