package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteDTO {
    @NotNull(message = "Total distance can not be null")
    @Positive(message = "Total distance should be positive")
    @NonNull
    private Float totalDistanceKm;

    @NotNull(message = "Completed can not be null")
    @NonNull
    private Boolean completed;

    private UserDTO user;

    private List<RouteStepDTO> routeSteps;
}
