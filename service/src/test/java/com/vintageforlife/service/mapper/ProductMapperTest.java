package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.dto.ProductDTO;
import com.vintageforlife.service.entity.AddressEntity;
import com.vintageforlife.service.entity.DistributionCenterEntity;
import com.vintageforlife.service.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ProductMapperTest {
    private final ProductMapper productMapper;

    @Autowired
    public ProductMapperTest(ProductMapper productMapper) {
        this.productMapper = productMapper;
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

        DistributionCenterDTO distributionCenterDTO = DistributionCenterDTO.builder()
                .name("Test Name")
                .address(addressDTO)
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .name("Test Name")
                .width(1f)
                .height(1f)
                .depth(1f)
                .distributionCenter(distributionCenterDTO)
                .build();

        ProductEntity productEntity = productMapper.toEntity(productDTO);

        assertEquals(productDTO.getName(), productEntity.getName());
        assertEquals(productDTO.getWidth(), productEntity.getWidth());
        assertEquals(productDTO.getHeight(), productEntity.getHeight());
        assertEquals(productDTO.getDepth(), productEntity.getDepth());
        assertEquals(productDTO.getDistributionCenter().getAddress().toString(), productEntity.getDistributionCenter().getAddress().toString());
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

        DistributionCenterEntity distributionCenterDTO = DistributionCenterEntity.builder()
                .name("Test Name")
                .address(addressEntity)
                .build();

        ProductEntity productEntity = ProductEntity.builder()
                .name("Test Name")
                .width(1f)
                .height(1f)
                .depth(1f)
                .distributionCenter(distributionCenterDTO)
                .build();

        ProductDTO productDTO = productMapper.toDTO(productEntity);

        assertEquals(productEntity.getName(), productDTO.getName());
        assertEquals(productEntity.getWidth(), productDTO.getWidth());
        assertEquals(productEntity.getHeight(), productDTO.getHeight());
        assertEquals(productEntity.getDepth(), productDTO.getDepth());
        assertNull(productDTO.getDistributionCenter());
    }
}
