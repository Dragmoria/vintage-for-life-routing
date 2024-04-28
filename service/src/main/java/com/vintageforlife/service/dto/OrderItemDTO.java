package com.vintageforlife.service.dto;

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

    @NotNull(message = "Quantity can not be null")
    @NonNull
    private Boolean completed;

    @NotNull(message = "Product can not be null")
    @NonNull
    private ProductDTO product;

    @NotNull(message = "Retour can not be null")
    @NonNull
    private Boolean retour;
}
