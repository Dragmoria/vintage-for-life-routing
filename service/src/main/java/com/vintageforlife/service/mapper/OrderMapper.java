package com.vintageforlife.service.mapper;

import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements Mapper<OrderEntity, OrderDTO> {
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public OrderMapper(CustomerMapper customerMapper, AddressMapper addressMapper) {
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public OrderEntity toEntity(OrderDTO dto) {
        return OrderEntity.builder()
                .date(dto.getDate())
                .customer(customerMapper.toEntity(dto.getCustomer()))
                .address(addressMapper.toEntity(dto.getAddress()))
                .retour(dto.getRetour())
                .build();
    }

    @Override
    public OrderDTO toDTO(OrderEntity entity) {
        return OrderDTO.builder()
                .customer(customerMapper.toDTO(entity.getCustomer()))
                .address(addressMapper.toDTO(entity.getAddress()))
                .id(entity.getId())
                .date(entity.getDate())
                .retour(entity.getRetour())
                .build();
    }
}
