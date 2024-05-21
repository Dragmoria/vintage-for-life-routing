package com.vintageforlife.service.routing;

import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.dto.ProductDTO;
import com.vintageforlife.service.routing.genetic.Truck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TruckTest {
    private Truck truck;

    @BeforeEach
    public void setUp() {
        truck = new Truck(2.0f, 2.0f);
    }

    @Test
    public void testAddItem() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setWidth(0.1f);
        productDTO.setDepth(0.1f);
        orderItemDTO.setProduct(productDTO);

        boolean result = truck.addItem(orderItemDTO);

        assertTrue(result, "Expected addItem to return true, but it returned false");
        assertEquals(1, truck.getAddedItems().size(), "Expected one item to be added to the truck");
        assertEquals(1, truck.getAddedOrders().size(), "Expected one order to be added to the truck");
    }

    @Test
    public void testAddItemWhenThereIsNoSpace() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setWidth(3.0f);
        productDTO.setDepth(3.0f);
        orderItemDTO.setProduct(productDTO);

        boolean result = truck.addItem(orderItemDTO);

        assertFalse(result, "Expected addItem to return false, but it returned true");
        assertTrue(truck.getAddedItems().isEmpty(), "Expected no items to be added to the truck");
        assertTrue(truck.getAddedOrders().isEmpty(), "Expected no orders to be added to the truck");
    }

    @Test
    void testAddingMultipleItems() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setWidth(1f);
        productDTO.setDepth(1f);
        orderItemDTO.setProduct(productDTO);

        boolean resultShouldFit1 = truck.addItem(orderItemDTO);
        boolean resultShouldFit2 = truck.addItem(orderItemDTO);
        boolean resultShouldFit3 = truck.addItem(orderItemDTO);
        boolean resultShouldFit4 = truck.addItem(orderItemDTO);
        boolean resultShouldNotFit = truck.addItem(orderItemDTO);

        assertTrue(resultShouldFit1, "Expected addItem to return true, but it returned false");
        assertTrue(resultShouldFit2, "Expected addItem to return true, but it returned false");
        assertTrue(resultShouldFit3, "Expected addItem to return true, but it returned false");
        assertTrue(resultShouldFit4, "Expected addItem to return true, but it returned false");
        assertFalse(resultShouldNotFit, "Expected addItem to return false, but it returned true");
    }
}
