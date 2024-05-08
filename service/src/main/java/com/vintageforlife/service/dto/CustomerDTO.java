package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    @NotBlank(message = "External id can not be blank")
    @NonNull
    private Integer externalId;

    @NotBlank(message = "Name can not be blank")
    @NonNull
    private String name;
}
