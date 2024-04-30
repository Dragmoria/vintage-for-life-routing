package com.vintageforlife.service.dto;

import com.vintageforlife.service.routing.Edge;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDTO {
    private String fromId;
    private String toId;
    private int weight;

    public EdgeDTO(Edge edge) {
        this.fromId = edge.getFrom().getId();
        this.toId = edge.getTo().getId();
        this.weight = edge.getWeight();
    }
}
