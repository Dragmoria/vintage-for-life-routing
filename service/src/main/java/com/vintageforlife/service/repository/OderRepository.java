package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OderRepository extends CrudRepository<OrderEntity, Integer> {
}
