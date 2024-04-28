package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.CustomerDTO;
import com.vintageforlife.service.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<CustomerEntity, CustomerDTO> {
    @Override
    public CustomerEntity toEntity(CustomerDTO dto) {
        return CustomerEntity.builder()
                .externalId(dto.getExternalId())
                .name(dto.getName())
                .build();
    }

    @Override
    public CustomerDTO toDTO(CustomerEntity entity) {
        return CustomerDTO.builder()
                .externalId(entity.getExternalId())
                .name(entity.getName())
                .build();
    }
}
