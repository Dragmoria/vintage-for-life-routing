package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.*;
import com.vintageforlife.service.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class RouteStepMapperTest {
    private final RouteStepMapper routeStepMapper;

    @Autowired
    public RouteStepMapperTest(RouteStepMapper routeStepMapper) {
        this.routeStepMapper = routeStepMapper;
    }

    @Test
    void testToEntity() {
        RouteDTO routeDTO = RouteDTO.builder()
                .totalDistanceKm(1f)
                .completed(false)
                .build();

        RouteStepDTO routeStepDTO = RouteStepDTO.builder()
                .id(1)
                .route(routeDTO)
                .stepIndex(1)
                .distanceKm(1f)
                .completed(false)
                .build();

        RouteStepEntity routeStepEntity = routeStepMapper.toEntity(routeStepDTO);

        assertEquals(routeStepDTO.getRoute().getTotalDistanceKm(), routeStepEntity.getRoute().getTotalDistanceKm());
        assertEquals(routeStepDTO.getStepIndex(), routeStepEntity.getStepIndex());
        assertEquals(routeStepDTO.getDistanceKm(), routeStepEntity.getDistanceKm());
        assertEquals(routeStepDTO.getCompleted(), routeStepEntity.getCompleted());
        assertNull(routeStepDTO.getOrder());
    }

    @Test
    void testToEntityWithOrder() {
        RouteDTO routeDTO = RouteDTO.builder()
                .totalDistanceKm(1f)
                .completed(false)
                .build();

        AddressDTO addressDTO = AddressDTO.builder()
                .postCode("12345")
                .street("Test Street")
                .city("Test City")
                .houseNumber(1)
                .extension("Test Extension")
                .build();

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("Test Name")
                .externalId(1)
                .build();

        OrderDTO orderDTO = OrderDTO.builder()
                .id(1)
                .address(addressDTO)
                .date(new Date())
                .retour(false)
                .customer(customerDTO)
                .build();

        RouteStepDTO routeStepDTO = RouteStepDTO.builder()
                .id(1)
                .route(routeDTO)
                .stepIndex(1)
                .distanceKm(1f)
                .completed(false)
                .order(orderDTO)
                .build();

        RouteStepEntity routeStepEntity = routeStepMapper.toEntity(routeStepDTO);

        assertEquals(routeStepDTO.getRoute().getTotalDistanceKm(), routeStepEntity.getRoute().getTotalDistanceKm());
        assertEquals(routeStepDTO.getStepIndex(), routeStepEntity.getStepIndex());
        assertEquals(routeStepDTO.getDistanceKm(), routeStepEntity.getDistanceKm());
        assertEquals(routeStepDTO.getCompleted(), routeStepEntity.getCompleted());
        assertEquals(routeStepDTO.getOrder().getAddress().toString(), routeStepEntity.getOrder().getAddress().toString());
    }

    @Test
    void testToDTO() {
        RouteEntity routeEntity = RouteEntity.builder()
                .totalDistanceKm(1f)
                .completed(false)
                .build();

        RouteStepEntity routeStepEntity = RouteStepEntity.builder()
                .id(1)
                .route(routeEntity)
                .stepIndex(1)
                .distanceKm(1f)
                .completed(false)
                .build();

        RouteStepDTO routeStepDTO = routeStepMapper.toDTO(routeStepEntity);

        assertNull(routeStepDTO.getRoute());
        assertEquals(routeStepEntity.getStepIndex(), routeStepDTO.getStepIndex());
        assertEquals(routeStepEntity.getDistanceKm(), routeStepDTO.getDistanceKm());
        assertEquals(routeStepEntity.getCompleted(), routeStepDTO.getCompleted());
        assertNull(routeStepDTO.getOrder());
    }

    @Test
    void testToDTOWithOrder() {
        RouteEntity routeEntity = RouteEntity.builder()
                .totalDistanceKm(1f)
                .completed(false)
                .build();

        AddressEntity addressEntity = AddressEntity.builder()
                .postcode("12345")
                .street("Test Street")
                .city("Test City")
                .houseNumber(1)
                .extension("Test Extension")
                .build();

        CustomerEntity customerEntity = CustomerEntity.builder()
                .name("Test Name")
                .externalId(1)
                .build();

        OrderEntity orderEntity = OrderEntity.builder()
                .id(1)
                .address(addressEntity)
                .date(new Date())
                .retour(false)
                .customer(customerEntity)
                .build();

        RouteStepEntity routeStepEntity = RouteStepEntity.builder()
                .id(1)
                .route(routeEntity)
                .stepIndex(1)
                .distanceKm(1f)
                .completed(false)
                .order(orderEntity)
                .build();

        RouteStepDTO routeStepDTO = routeStepMapper.toDTO(routeStepEntity);

        assertNull(routeStepDTO.getRoute());
        assertEquals(routeStepEntity.getStepIndex(), routeStepDTO.getStepIndex());
        assertEquals(routeStepEntity.getDistanceKm(), routeStepDTO.getDistanceKm());
        assertEquals(routeStepEntity.getCompleted(), routeStepDTO.getCompleted());
        assertNull(routeStepDTO.getOrder());
    }
}
