package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.dto.UserDTO;
import com.vintageforlife.service.entity.RouteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper implements Mapper<RouteEntity, RouteDTO>{
    private final UserMapper userMapper;

    @Autowired
    public RouteMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RouteEntity toEntity(RouteDTO dto) {
        return RouteEntity.builder()
                .totalDistanceKm(dto.getTotalDistanceKm())
                .completed(dto.getCompleted())
                .build();
    }

    public RouteDTO toDTO(RouteEntity entity) {
        UserDTO userDTO = null;
        if (entity.getUser() != null) {
            userDTO = userMapper.toDTO(entity.getUser());
        }

        return RouteDTO.builder()
                .id(entity.getId())
                .totalDistanceKm(entity.getTotalDistanceKm())
                .completed(entity.getCompleted())
                .user(userDTO)
                .build();
    }
}
