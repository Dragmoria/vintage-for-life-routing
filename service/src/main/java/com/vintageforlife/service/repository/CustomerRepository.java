package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer>{
    void deleteByExternalId(Integer externalId);
}
