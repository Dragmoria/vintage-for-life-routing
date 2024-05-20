package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.RouteStepDTO;
import com.vintageforlife.service.entity.OrderEntity;
import com.vintageforlife.service.entity.RouteStepEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteStepMapper implements Mapper<RouteStepEntity, RouteStepDTO>{
    private final RouteMapper routeMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public RouteStepMapper(RouteMapper routeMapper, OrderMapper orderMapper) {
        this.routeMapper = routeMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public RouteStepEntity toEntity(RouteStepDTO dto) {
        if (dto.getOrder() == null) {
            return RouteStepEntity.builder()
                    .stepIndex(dto.getStepIndex())
                    .distanceKm(dto.getDistanceKm())
                    .completed(dto.getCompleted())
                    .route(routeMapper.toEntity(dto.getRoute()))
                    .build();
        }

        return RouteStepEntity.builder()
                .stepIndex(dto.getStepIndex())
                .distanceKm(dto.getDistanceKm())
                .completed(dto.getCompleted())
                .route(routeMapper.toEntity(dto.getRoute()))
                .order(orderMapper.toEntity(dto.getOrder()))
                .build();
    }

    private List<OrderEntity> requestedOrders;

    @Override
    public RouteStepDTO toDTO(RouteStepEntity entity) {
        if (entity.getOrder() == null) {
            return RouteStepDTO.builder()
                    .id(entity.getId())
                    .stepIndex(entity.getStepIndex())
                    .distanceKm(entity.getDistanceKm())
                    .completed(entity.getCompleted())
                    .build();
        }

        return RouteStepDTO.builder()
                .id(entity.getId())
                .stepIndex(entity.getStepIndex())
                .distanceKm(entity.getDistanceKm())
                .completed(entity.getCompleted())
                .build();

    }
}
