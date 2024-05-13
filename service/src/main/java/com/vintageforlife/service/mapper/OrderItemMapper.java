package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.OrderItemDTO;
import com.vintageforlife.service.entity.OrderItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper implements Mapper<OrderItemEntity, OrderItemDTO> {
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;

    @Autowired
    public OrderItemMapper(OrderMapper orderMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
    }

    @Override
    public OrderItemEntity toEntity(OrderItemDTO dto) {
        return OrderItemEntity.builder()
                .product(productMapper.toEntity(dto.getProduct()))
                .order(orderMapper.toEntity(dto.getOrder()))
                .build();
    }

    @Override
    public OrderItemDTO toDTO(OrderItemEntity entity) {
        return OrderItemDTO.builder()
                .product(productMapper.toDTO(entity.getProduct()))
                .build();
    }
}
