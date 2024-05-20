package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {
    @NotNull(message = "Order can not be null")
    private OrderDTO order;

    @NotNull(message = "Product can not be null")
    private ProductDTO product;

    public OrderItemDTO() {
    }

    public OrderItemDTO(OrderDTO order, ProductDTO product) {
        this.order = order;
        this.product = product;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
