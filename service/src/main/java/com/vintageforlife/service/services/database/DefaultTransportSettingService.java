package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.TransportSettingDTO;
import com.vintageforlife.service.mapper.TransportSettingMapper;
import com.vintageforlife.service.repository.TransportSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultTransportSettingService implements TransportSettingService {
    private final TransportSettingRepository transportSettingRepository;
    private final TransportSettingMapper transportSettingMapper;

    @Autowired
    public DefaultTransportSettingService(TransportSettingRepository transportSettingRepository, TransportSettingMapper transportSettingMapper) {
        this.transportSettingRepository = transportSettingRepository;
        this.transportSettingMapper = transportSettingMapper;
    }

    @Override
    public List<TransportSettingDTO> getTransportSettingsForDistributionCenter(Integer distributionCenterId) {
        return transportSettingRepository.findByDistributionCenterId(distributionCenterId).orElseThrow(
                    () -> new IllegalArgumentException("Transport settings for distribution center with id " + distributionCenterId + " do not exist")
                ).stream()
                .map(transportSettingMapper::toDTO)
                .toList();
    }
}
