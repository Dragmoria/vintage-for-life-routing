package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message = "Name can not be blank")
    @NonNull
    private String name;

    @Positive(message = "Width should be positive")
    private float width;

    @Positive(message = "Height should be positive")
    private float height;

    @Positive(message = "Depth should be positive")
    private float depth;

    @NotNull(message = "DistributionCenter can not be null")
    private DistributionCenterDTO distributionCenter;
}
