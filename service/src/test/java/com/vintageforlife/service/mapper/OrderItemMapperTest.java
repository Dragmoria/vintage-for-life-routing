package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.*;
import com.vintageforlife.service.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderItemMapperTest {
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderItemMapperTest(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Test
    void testToEntity() {
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

        DistributionCenterDTO distributionCenterDTO = DistributionCenterDTO.builder()
                .name("Test Name")
                .address(addressDTO)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("Test Name")
                .distributionCenter(distributionCenterDTO)
                .build();


        OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                .order(orderDTO)
                .product(productDTO)
                .build();

        OrderItemEntity orderItemEntity = orderItemMapper.toEntity(orderItemDTO);

        assertEquals(orderItemDTO.getProduct().getName(), orderItemEntity.getProduct().getName());
        assertEquals(orderItemDTO.getOrder().getRetour(), orderItemEntity.getOrder().getRetour());
    }

    @Test
    void testToDTO() {
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

        DistributionCenterEntity distributionCenterEntity = DistributionCenterEntity.builder()
                .name("Test Name")
                .address(addressEntity)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .name("Test Name")
                .distributionCenter(distributionCenterEntity)
                .depth(1f)
                .height(1f)
                .width(1f)
                .build();

        OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                .order(orderEntity)
                .product(productEntity)
                .build();

        OrderItemDTO orderItemDTO = orderItemMapper.toDTO(orderItemEntity);

        assertEquals(orderItemEntity.getProduct().getName(), orderItemDTO.getProduct().getName());
    }
}
