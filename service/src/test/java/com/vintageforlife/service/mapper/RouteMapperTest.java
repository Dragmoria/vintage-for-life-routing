package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.entity.RouteEntity;
import com.vintageforlife.service.entity.UserEntity;
import com.vintageforlife.service.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class RouteMapperTest {
    private final RouteMapper routeMapper;

    @Autowired
    public RouteMapperTest(RouteMapper routeMapper) {
        this.routeMapper = routeMapper;
    }

    @Test
    void testToEntity() {
        RouteDTO routeDTO = RouteDTO.builder()
                .totalDistanceKm(1f)
                .completed(false)
                .build();

        RouteEntity routeEntity = routeMapper.toEntity(routeDTO);

        assertEquals(routeDTO.getTotalDistanceKm(), routeEntity.getTotalDistanceKm());
        assertEquals(routeDTO.getCompleted(), routeEntity.getCompleted());
    }

    @Test
    void testToDTO() {
        RouteEntity routeEntity = RouteEntity.builder()
                .id(1)
                .totalDistanceKm(1f)
                .completed(false)
                .build();

        RouteDTO routeDTO = routeMapper.toDTO(routeEntity);

        assertEquals(routeEntity.getTotalDistanceKm(), routeDTO.getTotalDistanceKm());
        assertEquals(routeEntity.getCompleted(), routeDTO.getCompleted());
        assertNull(routeDTO.getUser());
    }

    @Test
    void testToDTOWithUser() {
        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .name("Test Name")
                .password("Test Password")
                .role(Role.ADMIN)
                .email("test email")
                .build();

        RouteEntity routeEntity = RouteEntity.builder()
                .id(1)
                .totalDistanceKm(1f)
                .completed(false)
                .user(userEntity)
                .build();

        RouteDTO routeDTO = routeMapper.toDTO(routeEntity);

        assertEquals(routeEntity.getTotalDistanceKm(), routeDTO.getTotalDistanceKm());
        assertEquals(routeEntity.getCompleted(), routeDTO.getCompleted());
        assertEquals(routeEntity.getUser().getName(), routeDTO.getUser().getName());
    }
}
