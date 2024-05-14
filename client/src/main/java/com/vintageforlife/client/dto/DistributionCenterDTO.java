package com.vintageforlife.client.dto;

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
public class DistributionCenterDTO {
    @NotNull(message = "Address can not be null")
    private AddressDTO address;

    @NotBlank(message = "Name can not be blank")
    @NonNull
    private String name;
}
