package com.vintageforlife.service.services.googleApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SecondaryRow;

@Getter
@SecondaryRow
@NoArgsConstructor
@AllArgsConstructor
public class Row {
    @JsonProperty("elements")
    private Element[] elements;
}
