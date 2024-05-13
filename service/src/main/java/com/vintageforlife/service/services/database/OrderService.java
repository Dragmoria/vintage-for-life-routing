package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();

    OrderDTO getOrder(Integer id);
}
