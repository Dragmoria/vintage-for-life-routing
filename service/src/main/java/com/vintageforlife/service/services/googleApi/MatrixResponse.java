package com.vintageforlife.service.services.googleApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatrixResponse {
    @JsonProperty("rows")
    private Row[] rows;

    @JsonProperty("originAddresses")
    private String[] originAddresses;

    @JsonProperty("destinationAddresses")
    private String[] destinationAddresses;
}
