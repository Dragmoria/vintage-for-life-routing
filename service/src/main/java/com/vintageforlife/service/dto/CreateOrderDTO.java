package com.vintageforlife.service.dto;

import java.util.List;

public class CreateOrderDTO {
    private CustomerDTO customer;

    private AddressDTO address;

    private List<OrderItemDTO> orderItems;
}
