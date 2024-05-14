package com.vintageforlife.service.services.googleApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

import java.util.List;

@Getter
@Setter
@SecondaryRow
@NoArgsConstructor
@AllArgsConstructor
public class Row {
    @JsonProperty("elements")
    private List<Element> elements;
}
