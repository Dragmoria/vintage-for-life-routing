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
public class SimpleRouteStepDTO {
    @NotNull(message = "Id can not be null")
    @Positive(message = "Id should be positive")
    private Integer id;

    @NotNull(message = "Step index can not be null")
    private Boolean completed;
}
