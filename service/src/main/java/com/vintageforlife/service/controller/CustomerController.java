package com.vintageforlife.service.controller;

import com.vintageforlife.service.services.database.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.vintageforlife.service.dto.CustomerDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/customers", consumes = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<CustomerDTO>> createCustomer(@RequestBody @Valid List<CustomerDTO> customerDTOs) {
        List<CustomerDTO> createdCustomers = customerDTOs.stream().map(customerService::createCustomer).toList();
        return ResponseEntity.ok(createdCustomers);
    }

    @GetMapping(value = "/customers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @DeleteMapping(value = "{externalId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer externalId) {
        customerService.deleteCustomer(externalId);
        return ResponseEntity.noContent().build();
    }
}
