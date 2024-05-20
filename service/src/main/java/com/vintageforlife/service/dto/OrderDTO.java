package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    @JsonIgnore
    @NonNull
    private Integer id;

    @NotNull(message = "Customer can not be null")
    private CustomerDTO customer;

    @NotNull(message = "Address can not be null")
    @NonNull
    private AddressDTO address;

    @NotNull(message = "Date can not be null")
    @NonNull
    private Date date;

    @NotNull(message = "Retour can not be null")
    @NonNull
    private Boolean retour;

    @NotNull(message = "OrderItems can not be null")
    @Size(min = 1, message = "OrderItems should have at least one item")
    private List<OrderItemDTO> orderItems;
}
