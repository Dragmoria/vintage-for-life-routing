package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.RouteStepDTO;
import com.vintageforlife.service.entity.RouteStepEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteStepMapper implements Mapper<RouteStepEntity, RouteStepDTO>{
    private final OrderItemMapper orderItemMapper;
    private final RouteMapper routeMapper;

    @Autowired
    public RouteStepMapper(OrderItemMapper orderItemMapper, RouteMapper routeMapper) {
        this.orderItemMapper = orderItemMapper;
        this.routeMapper = routeMapper;
    }

    @Override
    public RouteStepEntity toEntity(RouteStepDTO dto) {
        return RouteStepEntity.builder()
                .stepIndex(dto.getStepIndex())
                .distanceKm(dto.getDistanceKm())
                .completed(dto.getCompleted())
                .orderItem(orderItemMapper.toEntity(dto.getOrderItem()))
                .route(routeMapper.toEntity(dto.getRoute()))
                .build();
    }

    @Override
    public RouteStepDTO toDTO(RouteStepEntity entity) {
        return RouteStepDTO.builder()
                .stepIndex(entity.getStepIndex())
                .distanceKm(entity.getDistanceKm())
                .completed(entity.getCompleted())
                .orderItem(orderItemMapper.toDTO(entity.getOrderItem()))
                .build();
    }
}
