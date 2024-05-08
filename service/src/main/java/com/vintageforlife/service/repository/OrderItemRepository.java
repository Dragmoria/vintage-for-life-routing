package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Integer> {
}
