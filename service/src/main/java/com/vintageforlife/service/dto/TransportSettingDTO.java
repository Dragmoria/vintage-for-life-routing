package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportSettingDTO {
    @NotBlank(message = "Name can not be blank")
    @NonNull
    private String name;

    @NotBlank(message = "Value can not be blank")
    @NonNull
    private String value;

    @NotNull(message = "DistributionCenter can not be null")
    @NonNull
    private DistributionCenterDTO distributionCenter;
}
