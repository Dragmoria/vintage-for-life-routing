package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.CustomerDTO;
import com.vintageforlife.service.entity.CustomerEntity;
import com.vintageforlife.service.mapper.CustomerMapper;
import com.vintageforlife.service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public DefaultCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO getCustomerByExternalId(Integer externalId) {
        return null;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
//        CustomerEntity customerEntity = CustomerMapper.toEntity(customerDTO);
//        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);
//
//        return CustomerMapper.toDTO(savedCustomerEntity);
        return null;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
//        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
//                .map(CustomerMapper::toDTO)
//                .collect(Collectors.toList());
        return null;
    }
}
