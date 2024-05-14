package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    @NotNull(message = "Order can not be null")
    private OrderDTO order;

    @NotNull(message = "Product can not be null")
    @NonNull
    private ProductDTO product;
}
