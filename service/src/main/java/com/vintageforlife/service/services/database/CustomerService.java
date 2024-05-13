package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerByExternalId(Integer externalId);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getAllCustomers();

    void deleteCustomer(Integer externalId);
}
