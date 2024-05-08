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
    private final AddressMapper addressMapper;

    @Autowired
    public DefaultAddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        AddressEntity addressEntity = addressMapper.toEntity(addressDTO);
        AddressEntity savedAddressEntity = addressRepository.save(addressEntity);
        return addressMapper.toDTO(savedAddressEntity);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        return StreamSupport.stream(addressRepository.findAll().spliterator(), false)
                .map(addressMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
