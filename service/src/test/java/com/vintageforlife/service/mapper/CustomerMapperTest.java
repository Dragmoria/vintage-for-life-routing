package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.CustomerDTO;
import com.vintageforlife.service.entity.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerMapperTest {
    private final CustomerMapper customerMapper;

    public CustomerMapperTest(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Test
    void testToEntity() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("Test Name")
                .externalId(1)
                .build();

        CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);

        assertEquals(customerDTO.getName(), customerEntity.getName());
        assertEquals(customerDTO.getExternalId(), customerEntity.getExternalId());
    }

    @Test
    void testToDTO() {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .name("Test Name")
                .externalId(1)
                .build();

        CustomerDTO customerDTO = customerMapper.toDTO(customerEntity);

        assertEquals(customerEntity.getName(), customerDTO.getName());
        assertEquals(customerEntity.getExternalId(), customerDTO.getExternalId());
    }
}
