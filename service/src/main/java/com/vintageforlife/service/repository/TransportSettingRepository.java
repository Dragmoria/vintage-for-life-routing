package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.TransportSettingEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransportSettingRepository extends CrudRepository<TransportSettingEntity, Integer> {
    Optional<List<TransportSettingEntity>> findByDistributionCenterId(Integer distributionCenterId);
}
