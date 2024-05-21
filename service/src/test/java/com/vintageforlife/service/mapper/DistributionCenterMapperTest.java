package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.entity.AddressEntity;
import com.vintageforlife.service.entity.DistributionCenterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class DistributionCenterMapperTest {
    private final DistributionCenterMapper distributionCenterMapper;

    @Autowired
    public DistributionCenterMapperTest(DistributionCenterMapper distributionCenterMapper) {
        this.distributionCenterMapper = distributionCenterMapper;
    }

    @Test
    void testToEntity() {
        DistributionCenterDTO distributionCenterDTO = DistributionCenterDTO.builder()
                .name("Test Name")
                .address(AddressDTO.builder()
                        .postCode("12345")
                        .street("Test Street")
                        .city("Test City")
                        .houseNumber(1)
                        .extension("Test Extension")
                        .build())
                .build();

        DistributionCenterEntity distributionCenterEntity = distributionCenterMapper.toEntity(distributionCenterDTO);

        assertEquals(distributionCenterDTO.getName(), distributionCenterEntity.getName());
        assertEquals(distributionCenterDTO.getAddress().toString(), distributionCenterEntity.getAddress().toString());
    }

    @Test
    void testToDTO() {
        DistributionCenterEntity distributionCenterEntity = DistributionCenterEntity.builder()
                .name("Test Name")
                .address(AddressEntity.builder()
                        .postcode("12345")
                        .street("Test Street")
                        .city("Test City")
                        .houseNumber(1)
                        .extension("Test Extension")
                        .build())
                .build();

        DistributionCenterDTO distributionCenterDTO = distributionCenterMapper.toDTO(distributionCenterEntity);

        assertEquals(distributionCenterDTO.getName(), distributionCenterEntity.getName());
        assertNull(distributionCenterDTO.getAddress());
    }
}
