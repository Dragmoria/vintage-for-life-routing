package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    @NotBlank(message = "External id can not be blank")
    private Integer externalId;

    @NotBlank(message = "Name can not be blank")
    private String name;

    public CustomerDTO(Integer externalId, String name) {
        this.externalId = externalId;
        this.name = name;
    }

    public CustomerDTO() {
    }

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
