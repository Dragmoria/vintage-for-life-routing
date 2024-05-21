package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.CustomerDTO;
import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.entity.AddressEntity;
import com.vintageforlife.service.entity.CustomerEntity;
import com.vintageforlife.service.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderMapperTest {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderMapperTest(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
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

        OrderEntity orderEntity = orderMapper.toEntity(orderDTO);

        assertEquals(orderDTO.getAddress().toString(), orderEntity.getAddress().toString());
        assertEquals(orderDTO.getCustomer().getName(), orderEntity.getCustomer().getName());
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
                .date(new Date())
                .customer(customerEntity)
                .address(addressEntity)
                .retour(false)
                .build();

        OrderDTO orderDTO = orderMapper.toDTO(orderEntity);

        assertEquals(orderEntity.getAddress().toString(), orderDTO.getAddress().toString());
        assertEquals(orderEntity.getCustomer().getName(), orderDTO.getCustomer().getName());
    }
}
