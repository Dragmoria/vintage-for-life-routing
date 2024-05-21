package com.vintageforlife.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderItemMapperTest {
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderItemMapperTest(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }


}
