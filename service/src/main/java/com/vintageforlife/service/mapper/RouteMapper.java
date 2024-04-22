package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.entity.RouteEntity;

public class RouteMapper {
    public static RouteDTO makeRouteDTO(RouteEntity entity) {
        RouteDTO.RouteDTOBuilder routeDTOBuilder = new RouteDTO.RouteDTOBuilder(
                entity.getId(),
                entity.getTotalDistanceKm(),
                UserMapper.makeUserDTO(entity.getUser())
        );

        if (entity.getCompleted() != null) {
            routeDTOBuilder.completed(entity.getCompleted());
        }

        return routeDTOBuilder.build();
    }

    public static RouteEntity makeRouteEntity(RouteDTO dto) {
        RouteEntity entity = new RouteEntity();
        entity.setTotalDistanceKm(dto.getTotalDistanceKm());
        entity.setCompleted(dto.getCompleted());
        entity.setUser(UserMapper.makeUserEntity(dto.getUser()));

        return entity;
    }
}
