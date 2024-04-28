package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.entity.AddressEntity;
import com.vintageforlife.service.entity.DistributionCenterEntity;
import com.vintageforlife.service.mapper.AddressMapper;
import com.vintageforlife.service.mapper.DistributionCenterMapper;
import com.vintageforlife.service.repository.DistributionCenterRepository;
import com.vintageforlife.service.services.database.DistributionCenterService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/public")
public class DistributionCenterController {
    private final DistributionCenterService distributionCenterService;
    private final DistributionCenterMapper distributionCenterMapper;
    private final AddressMapper addressMapper;

    @Autowired
    public DistributionCenterController(DistributionCenterService distributionCenterService, DistributionCenterMapper distributionCenterMapper, AddressMapper addressMapper) {
        this.distributionCenterService = distributionCenterService;
        this.distributionCenterMapper = distributionCenterMapper;
        this.addressMapper = addressMapper;
    }

    @GetMapping("/distribution-center")
    public ResponseEntity<?> getDistributionCenter() {
        DistributionCenterEntity distributionCenterEntity = distributionCenterService.fetchDistributionCenterEntity(1);
        DistributionCenterDTO distributionCenterDTO = distributionCenterMapper.toDTO(distributionCenterEntity);
        distributionCenterMapper.addToDTO(distributionCenterEntity.getAddress(), distributionCenterDTO);

        return ResponseEntity.ok(distributionCenterDTO);

    }

    @GetMapping("/distribution-centers")
    public ResponseEntity<?> getDistributionCenters() {
        List<DistributionCenterDTO> distributionCenterDTOS = distributionCenterService.getAllDistributionCenters();

        return ResponseEntity.ok(distributionCenterDTOS);
    }
}
