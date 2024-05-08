package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
}
