package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<AddressEntity, AddressDTO> {
    @Override
    public AddressEntity toEntity(AddressDTO dto) {
        return AddressEntity.builder()
                .postcode(dto.getPostCode())
                .street(dto.getStreet())
                .city(dto.getCity())
                .houseNumber(dto.getHouseNumber())
                .extension(dto.getExtension())
                .build();
    }

    @Override
    public AddressDTO toDTO(AddressEntity entity) {
        return AddressDTO.builder()
                .postCode(entity.getPostcode())
                .street(entity.getStreet())
                .city(entity.getCity())
                .houseNumber(entity.getHouseNumber())
                .extension(entity.getExtension())
                .build();
    }
}
