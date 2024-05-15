package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.routing.Node;

import java.util.ArrayList;
import java.util.List;

public class Truck {
    private Float truckWidth;

    private Float truckLength;

    private Rectangle rectangle;

    private List<OrderItemDTO> items;

    private List<Rectangle> remainingSpaces;

    public Truck(Float truckWidth, Float truckLength) {
        this.truckWidth = truckWidth;
        this.truckLength = truckLength;
        this.rectangle = new Rectangle(truckWidth, truckLength);
        this.items = new ArrayList<>();
    }

    public boolean addItem(OrderItemDTO orderItemDTO) {
        Rectangle productRectangle = new Rectangle(orderItemDTO.getProduct().getWidth(), orderItemDTO.getProduct().getDepth());

        // this needs to be done better for sure. currently it cant use the remaining length since i do max length minus max length for some reason. Also what if one of the items I add at the back are like really long? and and item could still fit next to it. I need to devide the spaces better.... HARD?? yes.... tomorrow

        if (!rectangle.canFit(productRectangle)) {
            rectangle = new Rectangle(truckWidth, truckLength - rectangle.getLength());

            if(!rectangle.canFit(productRectangle)) {
                return false;
            }
        }

        items.add(orderItemDTO);

        rectangle = new Rectangle(rectangle.getWidth() - productRectangle.getWidth(), truckLength);

        return true;
    }
}
