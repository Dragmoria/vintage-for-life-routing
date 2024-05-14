package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.OrderDTO;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();

    OrderDTO getOrder(Integer id);

    List<OrderDTO> getOrdersForDay(Date date);

    List<OrderDTO> getAllOrdersFromLast24Hours();
}
