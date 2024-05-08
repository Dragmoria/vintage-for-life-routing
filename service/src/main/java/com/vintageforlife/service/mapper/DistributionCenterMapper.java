package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.entity.DistributionCenterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistributionCenterMapper implements Mapper<DistributionCenterEntity, DistributionCenterDTO> {
    private final AddressMapper addressMapper;

    @Autowired
    public DistributionCenterMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public DistributionCenterEntity toEntity(DistributionCenterDTO dto) {
        return DistributionCenterEntity.builder()
                .address(addressMapper.toEntity(dto.getAddress()))
                .name(dto.getName())
                .build();
    }

    @Override
    public DistributionCenterDTO toDTO(DistributionCenterEntity entity) {
        return DistributionCenterDTO.builder()
                .name(entity.getName())
                .build();
    }
}
