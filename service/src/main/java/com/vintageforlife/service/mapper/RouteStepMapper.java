package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.dto.RouteStepDTO;
import com.vintageforlife.service.entity.RouteStepEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteStepMapper implements Mapper<RouteStepEntity, RouteStepDTO>{
    private final RouteMapper routeMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public RouteStepMapper(RouteMapper routeMapper, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.routeMapper = routeMapper;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public RouteStepEntity toEntity(RouteStepDTO dto) {
        return RouteStepEntity.builder()
                .stepIndex(dto.getStepIndex())
                .distanceKm(dto.getDistanceKm())
                .completed(dto.getCompleted())
                .route(routeMapper.toEntity(dto.getRoute()))
                .order(orderMapper.toEntity(dto.getOrder()))
                .build();
    }

    @Override
    public RouteStepDTO toDTO(RouteStepEntity entity) {
        List<OrderItemDTO> orderItemDTOList = entity.getOrder().getOrderItems().stream().map(orderItemMapper::toDTO).toList();
        OrderDTO orderDTO = orderMapper.toDTO(entity.getOrder());
        orderDTO.setOrderItems(orderItemDTOList);

        return RouteStepDTO.builder()
                .id(entity.getId())
                .stepIndex(entity.getStepIndex())
                .distanceKm(entity.getDistanceKm())
                .completed(entity.getCompleted())
                .order(orderDTO)
                .build();

    }
}
