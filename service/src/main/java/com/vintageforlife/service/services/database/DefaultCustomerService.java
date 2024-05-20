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
    private final CustomerMapper customerMapper;

    @Autowired
    public DefaultCustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO getCustomerByExternalId(Integer externalId) {
        return null;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);

        return customerMapper.toDTO(savedCustomerEntity);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Integer externalId) {
        customerRepository.deleteByExternalId(externalId);
    }
}
