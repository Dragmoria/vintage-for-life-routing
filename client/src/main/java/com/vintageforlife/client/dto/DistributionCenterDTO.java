package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistributionCenterDTO {
    @NotNull(message = "Address can not be null")
    private AddressDTO address;

    @NotBlank(message = "Name can not be blank")
    private String name;

    public DistributionCenterDTO() {
    }

    public DistributionCenterDTO(AddressDTO address, String name) {
        this.address = address;
        this.name = name;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
