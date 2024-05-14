package com.vintageforlife.service.services.googleApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatrixResponse {
    @JsonProperty("rows")
    private List<Row> rows;

    @JsonProperty("originAddresses")
    private List<String> originAddresses;

    @JsonProperty("destinationAddresses")
    private List<String> destinationAddresses;
}
