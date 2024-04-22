package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.RouteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepository extends CrudRepository<RouteEntity, Integer> {
    List<RouteEntity> findByUserId(Integer userId);
}
