package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.TransportSettingDTO;

import java.util.List;

public interface TransportSettingService {
    List<TransportSettingDTO> getTransportSettingsForDistributionCenter(Integer distributionCenterId);
}
