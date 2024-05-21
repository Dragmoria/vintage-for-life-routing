package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.entity.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressMapperTest {
    private final AddressMapper addressMapper;

    @Autowired
    public AddressMapperTest(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Test
    void testToEntity() {
        AddressDTO dto = AddressDTO.builder()
                .postCode("12345")
                .street("Test Street")
                .city("Test City")
                .houseNumber(1)
                .extension("Test Extension")
                .build();

        AddressEntity entity = addressMapper.toEntity(dto);

        assertEquals(dto.getPostCode(), entity.getPostcode());
        assertEquals(dto.getStreet(), entity.getStreet());
        assertEquals(dto.getCity(), entity.getCity());
        assertEquals(dto.getHouseNumber(), entity.getHouseNumber());
        assertEquals(dto.getExtension(), entity.getExtension());
    }

    @Test
    void testToDTO() {
        AddressEntity entity = AddressEntity.builder()
                .postcode("12345")
                .street("Test Street")
                .city("Test City")
                .houseNumber(1)
                .extension("Test Extension")
                .build();

        AddressDTO dto = addressMapper.toDTO(entity);

        assertEquals(entity.getPostcode(), dto.getPostCode());
        assertEquals(entity.getStreet(), dto.getStreet());
        assertEquals(entity.getCity(), dto.getCity());
        assertEquals(entity.getHouseNumber(), dto.getHouseNumber());
        assertEquals(entity.getExtension(), dto.getExtension());
    }

    @Test
    void testToString() {
        AddressDTO dto = AddressDTO.builder()
                .postCode("12345")
                .street("Test Street")
                .city("Test City")
                .houseNumber(1)
                .extension("Test Extension")
                .build();

        String expected = "Test Street 1Test Extension, 12345 Test City, Netherlands";
        assertEquals(expected, dto.toString());
    }
}
