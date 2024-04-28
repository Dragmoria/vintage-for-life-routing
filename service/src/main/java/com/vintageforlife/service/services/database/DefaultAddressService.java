package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.entity.AddressEntity;
import com.vintageforlife.service.mapper.AddressMapper;
import com.vintageforlife.service.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class DefaultAddressService implements AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public DefaultAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
//        AddressEntity addressEntity = AddressMapper.toEntity(addressDTO);
//        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
//        return AddressMapper.toDTO(savedAddressEntity);
        return null;
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
//        return StreamSupport.stream(addressRepository.findAll().spliterator(), false)
//                .map(AddressMapper::toDTO)
//                .collect(java.util.stream.Collectors.toList());
        return null;
    }
}
