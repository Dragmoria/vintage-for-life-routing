package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.entity.OrderEntity;
import com.vintageforlife.service.entity.OrderItemEntity;
import com.vintageforlife.service.mapper.OrderItemMapper;
import com.vintageforlife.service.mapper.OrderMapper;
import com.vintageforlife.service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class DefaultOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public DefaultOrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .map(orderEntity -> {
                    List<OrderItemEntity> orderItemEntities = orderEntity.getOrderItems();
                    List<OrderItemDTO> orderItemDTOs = orderItemEntities.stream().map(orderItemMapper::toDTO).toList();

                    OrderDTO orderDTO = orderMapper.toDTO(orderEntity);
                    orderDTO.setOrderItems(orderItemDTOs);

                    return orderDTO;
                }).toList();
    }

    @Override
    public OrderDTO getOrder(Integer id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order with id " + id + " does not exist"));

        List<OrderItemEntity> orderItemEntities = orderEntity.getOrderItems();
        List<OrderItemDTO> orderItemDTOs = orderItemEntities.stream().map(orderItemMapper::toDTO).toList();

        OrderDTO orderDTO = orderMapper.toDTO(orderEntity);
        orderDTO.setOrderItems(orderItemDTOs);

        return orderDTO;
    }
}
