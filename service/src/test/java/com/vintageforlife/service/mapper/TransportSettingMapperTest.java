package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.dto.TransportSettingDTO;
import com.vintageforlife.service.entity.AddressEntity;
import com.vintageforlife.service.entity.DistributionCenterEntity;
import com.vintageforlife.service.entity.TransportSettingEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransportSettingMapperTest {
    private final TransportSettingMapper transportSettingMapper;

    @Autowired
    public TransportSettingMapperTest(TransportSettingMapper transportSettingMapper) {
        this.transportSettingMapper = transportSettingMapper;
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

        TransportSettingDTO transportSettingDTO = TransportSettingDTO.builder()
                .name("Test Name")
                .value("Test Value")
                .distributionCenter(distributionCenterDTO)
                .build();

        TransportSettingEntity transportSettingEntity = transportSettingMapper.toEntity(transportSettingDTO);

        assertEquals(transportSettingDTO.getName(), transportSettingEntity.getName());
        assertEquals(transportSettingDTO.getValue(), transportSettingEntity.getValue());
        assertEquals(transportSettingDTO.getDistributionCenter().getName(), transportSettingEntity.getDistributionCenter().getName());
        assertEquals(transportSettingDTO.getDistributionCenter().getAddress().toString(), transportSettingEntity.getDistributionCenter().getAddress().toString());
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

        TransportSettingEntity transportSettingEntity = TransportSettingEntity.builder()
                .name("Test Name")
                .value("Test Value")
                .distributionCenter(distributionCenterEntity)
                .build();

        TransportSettingDTO transportSettingDTO = transportSettingMapper.toDTO(transportSettingEntity);

        assertEquals(transportSettingDTO.getName(), transportSettingEntity.getName());
        assertEquals(transportSettingDTO.getValue(), transportSettingEntity.getValue());
        assertEquals(transportSettingDTO.getDistributionCenter().getName(), transportSettingEntity.getDistributionCenter().getName());
    }
}
