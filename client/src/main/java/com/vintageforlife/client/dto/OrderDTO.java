package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    @NotNull(message = "Customer can not be null")
    private CustomerDTO customer;

    @NotNull(message = "Address can not be null")
    private AddressDTO address;

    @NotNull(message = "Date can not be null")
    private Date date;

    @NotNull(message = "Retour can not be null")
    private Boolean retour;

    @NotNull(message = "OrderItems can not be null")
    @Size(min = 1, message = "OrderItems should have at least one item")
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(CustomerDTO customer, AddressDTO address, Date date, Boolean retour, List<OrderItemDTO> orderItems) {
        this.customer = customer;
        this.address = address;
        this.date = date;
        this.retour = retour;
        this.orderItems = orderItems;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getRetour() {
        return retour;
    }

    public void setRetour(Boolean retour) {
        this.retour = retour;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
