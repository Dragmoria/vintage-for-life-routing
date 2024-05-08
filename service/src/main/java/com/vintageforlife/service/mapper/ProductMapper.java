package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.ProductDTO;
import com.vintageforlife.service.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<ProductEntity, ProductDTO> {
    private final DistributionCenterMapper distributionCenterMapper;

    @Autowired
    public ProductMapper(DistributionCenterMapper distributionCenterMapper) {
        this.distributionCenterMapper = distributionCenterMapper;
    }

    @Override
    public ProductEntity toEntity(ProductDTO dto) {
        return ProductEntity.builder()
                .name(dto.getName())
                .width(dto.getWidth())
                .height(dto.getHeight())
                .depth(dto.getDepth())
                .distributionCenter(distributionCenterMapper.toEntity(dto.getDistributionCenter()))
                .build();
    }

    @Override
    public ProductDTO toDTO(ProductEntity entity) {
        return ProductDTO.builder()
                .name(entity.getName())
                .width(entity.getWidth())
                .height(entity.getHeight())
                .depth(entity.getDepth())
                .build();
    }
}
