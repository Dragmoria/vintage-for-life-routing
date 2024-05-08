package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.TransportSettingDTO;
import com.vintageforlife.service.entity.TransportSettingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransportSettingMapper implements Mapper<TransportSettingEntity, TransportSettingDTO> {
    private final DistributionCenterMapper distributionCenterMapper;

    @Autowired
    public TransportSettingMapper(DistributionCenterMapper distributionCenterMapper) {
        this.distributionCenterMapper = distributionCenterMapper;
    }

    @Override
    public TransportSettingEntity toEntity(TransportSettingDTO dto) {
        return TransportSettingEntity.builder()
                .name(dto.getName())
                .value(dto.getValue())
                .distributionCenter(distributionCenterMapper.toEntity(dto.getDistributionCenter()))
                .build();
    }

    @Override
    public TransportSettingDTO toDTO(TransportSettingEntity entity) {
        return TransportSettingDTO.builder()
                .name(entity.getName())
                .value(entity.getValue())
                .distributionCenter(distributionCenterMapper.toDTO(entity.getDistributionCenter()))
                .build();
    }
}
