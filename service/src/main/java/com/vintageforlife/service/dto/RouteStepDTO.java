package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStepDTO {
    @NotNull(message = "Step index can not be null")
    @Positive(message = "Step index should be positive")
    @NonNull
    private Integer stepIndex;

    @NotNull(message = "Distance can not be null")
    @Positive(message = "Distance should be positive")
    @NonNull
    private Float distanceKm;

    @NotNull(message = "Completed can not be null")
    @NonNull
    private Boolean completed;

    @NotNull(message = "Route can not be null")
    private RouteDTO route;

    @NotNull(message = "Order can not be null")
    private OrderDTO order;
}
