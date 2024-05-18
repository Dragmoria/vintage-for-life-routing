package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.DistributionCenterDTO;

import java.util.List;

public interface DistributionCenterService {
    List<DistributionCenterDTO> getAllDistributionCenters();

    DistributionCenterDTO getDistributionCenterById(Integer id);
}
