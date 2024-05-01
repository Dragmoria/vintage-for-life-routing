package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateOrderItemDTO {
    @JsonIgnore
    private final Boolean completed = true;


}
