package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.entity.DistributionCenterEntity;
import com.vintageforlife.service.mapper.AddressMapper;
import com.vintageforlife.service.mapper.DistributionCenterMapper;
import com.vintageforlife.service.repository.DistributionCenterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DistributionCenterService {
    private final DistributionCenterRepository distributionCenterRepository;
    private final DistributionCenterMapper distributionCenterMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public DistributionCenterService(DistributionCenterRepository distributionCenterRepository, DistributionCenterMapper distributionCenterMapper, AddressMapper addressMapper) {
        this.distributionCenterRepository = distributionCenterRepository;
        this.distributionCenterMapper = distributionCenterMapper;
        this.addressMapper = addressMapper;
    }

    @Transactional
    public DistributionCenterEntity fetchDistributionCenterEntity(int id) {
        DistributionCenterEntity distributionCenterEntity = distributionCenterRepository.findById(id).get();
        distributionCenterEntity.getAddress();
        return distributionCenterEntity;
    }

    @Transactional
    public List<DistributionCenterDTO> getAllDistributionCenters() {
//        return StreamSupport.stream(distributionCenterRepository.findAll().spliterator(), false)
//                .map(e -> {
//                    DistributionCenterDTO distributionCenterDTO = distributionCenterMapper.toDTO(e);
//                    distributionCenterMapper.addToDTO(e.getAddress(), distributionCenterDTO);
//                    return distributionCenterDTO;
//                })
//                .collect(Collectors.toList());


        return StreamSupport.stream(distributionCenterRepository.findAll().spliterator(), false)
                .map(e -> {
                    DistributionCenterDTO distributionCenterDTO = distributionCenterMapper.toDTO(e);
                    distributionCenterDTO.setAddress(addressMapper.toDTO(e.getAddress()));
                    return distributionCenterDTO;
                })
                .collect(Collectors.toList());
    }
}
